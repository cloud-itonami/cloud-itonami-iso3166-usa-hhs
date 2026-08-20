(ns statute.facts-test
  "Offline invariants for the USA-HHS compliance catalog.

  These tests deliberately do NOT reach the network -- that is
  `tools/verify_citations.cljs`, which re-fetches the eCFR APIs and is the only
  thing that can tell you whether a citation is still true. What these tests
  pin is the shape the live gate depends on: if the catalog stops carrying the
  fields the gate reads, the gate degrades into checking less and still exits
  0, which is the failure mode where a green light means nothing."
  (:require [clojure.test :refer [deftest is testing]]
            [clojure.string :as str]
            [statute.facts :as f]))

(def all (f/entries))

(def hats #{:hipaa-ocr :cms-program :part2-contrast :grants-baseline :sibling-hhs})

(deftest catalog-is-not-empty
  (testing "a catalog that shrank to nothing must fail here, not pass silently"
    (is (seq all))
    (is (>= (count all) 39)
        "the live gate's default --min is 39 headings")))

(deftest catalog-key-is-the-blueprint-key
  (is (= #{"USA-HHS"} (set (keys f/catalog)))
      "this leaf carries exactly one ISO key and it must match blueprint.edn"))

(deftest ids-are-unique
  (let [ids (map :statute/id all)]
    (is (= (count ids) (count (distinct ids)))
        (str "duplicate :statute/id -- "
             (pr-str (map key (filter #(> (val %) 1) (frequencies ids))))))))

(deftest every-entry-has-what-the-gate-walks
  (doseq [e all]
    (testing (str (:statute/id e))
      (is (keyword? (:statute/id e)))
      (is (string? (:statute/title e)))
      (is (integer? (:statute/cfr-title e)))
      (is (vector? (:statute/cfr-node e)))
      (is (seq (:statute/cfr-node e)))
      (is (every? (fn [step]
                    (and (vector? step) (= 2 (count step))
                         (every? string? step)))
                  (:statute/cfr-node e)))
      (is (string? (:statute/verified-label e)))
      (is (not (str/blank? (:statute/verified-label e))))
      (is (string? (:statute/verified-at e)))
      (is (= :operative (:statute/status e)))
      (is (contains? hats (:statute/hat e))
          (str "unknown hat " (pr-str (:statute/hat e)))))))

(deftest every-entry-cites-a-declared-structure-endpoint
  (doseq [e all]
    (is (contains? f/ecfr-structure-api (:statute/cfr-title e))
        (str (:statute/id e) " cites CFR title " (:statute/cfr-title e)
             " but no structure endpoint is declared for it"))))

(deftest urls-are-ecfr
  (doseq [e all]
    (is (str/starts-with? (:statute/url e) "https://www.ecfr.gov/")
        (str (:statute/id e) " must cite the official eCFR"))))

(deftest quoted-entries-carry-the-fetch-coordinates
  (doseq [e (f/quoted-entries)]
    (testing (str (:statute/id e))
      (is (string? (:statute/quote-part e)))
      (is (string? (:statute/quote-section e)))
      (is (contains? f/ecfr-full-text-api (:statute/cfr-title e)))
      (is (vector? (:statute/verified-quotes e)))
      (is (every? #(and (string? %) (not (str/blank? %)))
                  (:statute/verified-quotes e)))
      (is (every? #(> (count %) 20) (:statute/verified-quotes e))))))

(deftest entries-without-a-full-text-endpoint-carry-no-quotes
  (doseq [e all]
    (when-not (contains? f/ecfr-full-text-api (:statute/cfr-title e))
      (is (empty? (:statute/verified-quotes e))
          (str (:statute/id e) " is in title " (:statute/cfr-title e)
               ", which has no declared full-text endpoint")))))

(deftest load-bearing-sections-keep-paired-spans
  (testing "HIPAA definition and Privacy general-rule sections carry two spans"
    (doseq [[id n] {:hipaa-160-103-definitions 2
                    :hipaa-164-502-uses 2}]
      (let [e (first (filter #(= id (:statute/id %)) all))]
        (is (some? e) (str "entry " id " has gone missing"))
        (is (= n (count (:statute/verified-quotes e)))
            (str id " must carry exactly " n " verified spans"))))))

(deftest total-quote-count-meets-the-gate-floor
  (is (>= (f/quote-count) 7)
      "the live gate's default --min-quotes is 7"))

(deftest every-absence-has-a-claim-and-a-see-instead
  (doseq [a f/absences]
    (testing (str (:absence/id a))
      (is (keyword? (:absence/id a)))
      (is (string? (:absence/claim a)))
      (is (> (count (:absence/claim a)) 100))
      (is (map? (:absence/see-instead a))))))

(deftest every-absence-carries-exactly-one-kind-of-claim
  (doseq [a f/absences]
    (let [kinds (filterv #(contains? a %)
                         [:absence/absent-part :absence/absent-label
                          :absence/absent-text])]
      (is (= 1 (count kinds))
          (str (:absence/id a) " must make exactly one kind of negative claim,
                got " (pr-str kinds))))))

(deftest every-absence-has-a-control
  (doseq [a f/absences]
    (testing (str (:absence/id a))
      (cond
        (:absence/absent-part a)
        (is (string? (get-in a [:absence/control-part :statute/part])))
        (:absence/absent-label a)
        (is (string? (get-in a [:absence/control-label :statute/pattern])))
        (:absence/absent-text a)
        (is (string? (get-in a [:absence/control-text :statute/pattern])))))))

(deftest control-is-not-the-same-as-the-absent-pattern
  (doseq [a f/absences]
    (let [absent  (or (get-in a [:absence/absent-label :statute/pattern])
                      (get-in a [:absence/absent-text :statute/pattern])
                      (get-in a [:absence/absent-part :statute/part]))
          control (or (get-in a [:absence/control-label :statute/pattern])
                      (get-in a [:absence/control-text :statute/pattern])
                      (get-in a [:absence/control-part :statute/part]))]
      (is (not= absent control)
          (str (:absence/id a) " -- control identical to absent pattern")))))

(deftest absence-patterns-compile
  (doseq [a f/absences
          k [[:absence/absent-label :statute/pattern]
             [:absence/absent-text :statute/pattern]
             [:absence/control-label :statute/pattern]
             [:absence/control-text :statute/pattern]]]
    (when-let [p (get-in a k)]
      (is (re-pattern p)
          (str (:absence/id a) " " (pr-str k) " must be a valid regex")))))

(deftest every-absence-scans-a-declared-title
  (doseq [a f/absences]
    (let [t (or (get-in a [:absence/absent-part :statute/cfr-title])
                (get-in a [:absence/absent-label :statute/cfr-title])
                (get-in a [:absence/absent-text :statute/cfr-title]))]
      (is (integer? t) (str (:absence/id a) " must name the CFR title it scans"))
      (is (contains? f/ecfr-structure-api t)))))

(deftest absence-count-meets-the-gate-floor
  (is (>= (count f/absences) 4)
      "the live gate's default --min-absences is 4"))

(deftest the-central-correction-is-present
  (testing "this leaf exists to pin HIPAA ≠ CMS ≠ Part 2 ≠ Uniform Guidance"
    (is (some? (first (filter #(= :no-cms-payment-vocabulary-in-hipaa-parts
                                  (:absence/id %))
                              f/absences))))
    (is (some? (first (filter #(= :no-uniform-guidance-in-hipaa-part-160
                                  (:absence/id %))
                              f/absences))))
    (is (some? (first (filter #(= :no-part2-sud-label-in-hipaa-privacy-subpart
                                  (:absence/id %))
                              f/absences))))
    (is (some? (first (filter #(= :no-hipaa-privacy-rule-title-in-part2
                                  (:absence/id %))
                              f/absences))))
    (is (some? (first (filter #(= :cms-chapter-iv (:statute/id %)) all))))
    (is (some? (first (filter #(= :ug-part-200 (:statute/id %)) all))))
    (is (some? (first (filter #(= :part2-sud-records (:statute/id %)) all))))))

(deftest every-hat-is-worn-by-something
  (doseq [h hats]
    (is (seq (f/by-hat h))
        (str "no entry wears " h))))

(deftest hhs-spans-three-titles
  (is (= [2 42 45] (f/titles-covered))
      "Uniform Guidance (2), CMS/Part2 (42), and HIPAA/HIT (45)"))

(deftest accessors-agree-with-the-catalog
  (is (= (count all) (count (mapcat val f/catalog))))
  (is (= (f/quote-count)
         (reduce + 0 (map #(count (:statute/verified-quotes %)) (f/quoted-entries))))))
