(ns statute.facts
  "Agency-level compliance catalog for **USA-HHS** (United States Department
  of Health and Human Services) -- the spec-basis behind this leaf's blueprint
  claim that an independent operator can navigate HHS award eligibility and
  health-data (HIPAA-adjacent) readiness.

  Scope. Country-wide U.S. federal statutes live in `cloud-itonami-iso3166-usa`
  and are NOT duplicated here. Sibling agency leaves hold their own chapters.

  Provenance. Every entry cites the official eCFR. `:statute/verified-label`
  is the byte-exact `label_description` from the eCFR versioner structure API
  on `:statute/verified-at`. Quote spans are byte-exact from the full-text API.
  `tools/verify_citations.cljs` re-fetches both and fails on drift.

  THE TRAP THIS CATALOG EXISTS TO PIN DOWN. **HIPAA Privacy/Security
  (45 CFR parts 160/162/164, OCR) is not CMS program/payment rules
  (42 CFR chapter IV), is not 42 CFR part 2 (SUD confidentiality), and is not
  Uniform Guidance for Federal awards (2 CFR part 200).** The blueprint mixes
  award eligibility and HIPAA-adjacent readiness in one sentence; the
  regulations keep those books apart, and the absences below record that."
  (:require [clojure.string :as str]))

;; AUTO-COUNT 39

(def ecfr-structure-api
  {45 "https://www.ecfr.gov/api/versioner/v1/structure/2026-08-18/title-45.json"
   42 "https://www.ecfr.gov/api/versioner/v1/structure/2026-08-18/title-42.json"
   2  "https://www.ecfr.gov/api/versioner/v1/structure/2026-08-18/title-2.json"})

(def ecfr-full-text-api
  "Full-text only for titles that carry quotes or text-absences."
  {45 "https://www.ecfr.gov/api/versioner/v1/full/2026-08-18/title-45.xml"
   42 "https://www.ecfr.gov/api/versioner/v1/full/2026-08-18/title-42.xml"
   2  "https://www.ecfr.gov/api/versioner/v1/full/2026-08-18/title-2.xml"})

(def catalog
  "USA-HHS -> ordered vector of verified regulatory anchors."
  {"USA-HHS"
   [
    {:statute/id :hipaa-part-160
     :statute/hat :hipaa-ocr
     :statute/topic #{:hipaa :hipaa-admin}
     :statute/title "45 CFR part 160 — General Administrative Requirements"
     :statute/cfr-title 45
     :statute/cfr-node [["subtitle" "A"] ["subchapter" "C"] ["part" "160"]]
     :statute/url "https://www.ecfr.gov/current/title-45/subtitle-A/subchapter-C/part-160"
     :statute/verified-label "General Administrative Requirements"
     :statute/verified-via "https://www.ecfr.gov/api/versioner/v1/structure/2026-08-18/title-45.json"
     :statute/verified-at "2026-08-18"
     :statute/status :operative
     :statute/note "HIPAA Administrative Simplification home for part 160. OCR enforces; this is not CMS payment rules."}
    {:statute/id :hipaa-part-162
     :statute/hat :hipaa-ocr
     :statute/topic #{:hipaa :hipaa-transactions}
     :statute/title "45 CFR part 162 — Administrative Requirements"
     :statute/cfr-title 45
     :statute/cfr-node [["subtitle" "A"] ["subchapter" "C"] ["part" "162"]]
     :statute/url "https://www.ecfr.gov/current/title-45/subtitle-A/subchapter-C/part-162"
     :statute/verified-label "Administrative Requirements"
     :statute/verified-via "https://www.ecfr.gov/api/versioner/v1/structure/2026-08-18/title-45.json"
     :statute/verified-at "2026-08-18"
     :statute/status :operative
     :statute/note "HIPAA Administrative Simplification home for part 162. OCR enforces; this is not CMS payment rules."}
    {:statute/id :hipaa-part-164
     :statute/hat :hipaa-ocr
     :statute/topic #{:hipaa :hipaa-security-privacy}
     :statute/title "45 CFR part 164 — Security and Privacy"
     :statute/cfr-title 45
     :statute/cfr-node [["subtitle" "A"] ["subchapter" "C"] ["part" "164"]]
     :statute/url "https://www.ecfr.gov/current/title-45/subtitle-A/subchapter-C/part-164"
     :statute/verified-label "Security and Privacy"
     :statute/verified-via "https://www.ecfr.gov/api/versioner/v1/structure/2026-08-18/title-45.json"
     :statute/verified-at "2026-08-18"
     :statute/status :operative
     :statute/note "HIPAA Administrative Simplification home for part 164. OCR enforces; this is not CMS payment rules."}
    {:statute/id :hipaa-164-subpart-a
     :statute/hat :hipaa-ocr
     :statute/topic #{:hipaa :hipaa-general}
     :statute/title "45 CFR part 164 subpart A — General Provisions"
     :statute/cfr-title 45
     :statute/cfr-node [["subtitle" "A"] ["subchapter" "C"] ["part" "164"] ["subpart" "A"]]
     :statute/url "https://www.ecfr.gov/current/title-45/subtitle-A/subchapter-C/part-164/subpart-A"
     :statute/verified-label "General Provisions"
     :statute/verified-via "https://www.ecfr.gov/api/versioner/v1/structure/2026-08-18/title-45.json"
     :statute/verified-at "2026-08-18"
     :statute/status :operative
     :statute/note "Security vs Privacy vs Breach are separate subparts under the same part."}
    {:statute/id :hipaa-164-subpart-c
     :statute/hat :hipaa-ocr
     :statute/topic #{:hipaa :hipaa-security}
     :statute/title "45 CFR part 164 subpart C — Security Standards for the Protection of Electronic Protected Health Information"
     :statute/cfr-title 45
     :statute/cfr-node [["subtitle" "A"] ["subchapter" "C"] ["part" "164"] ["subpart" "C"]]
     :statute/url "https://www.ecfr.gov/current/title-45/subtitle-A/subchapter-C/part-164/subpart-C"
     :statute/verified-label "Security Standards for the Protection of Electronic Protected Health Information"
     :statute/verified-via "https://www.ecfr.gov/api/versioner/v1/structure/2026-08-18/title-45.json"
     :statute/verified-at "2026-08-18"
     :statute/status :operative
     :statute/note "Security vs Privacy vs Breach are separate subparts under the same part."}
    {:statute/id :hipaa-164-subpart-d
     :statute/hat :hipaa-ocr
     :statute/topic #{:hipaa :hipaa-breach}
     :statute/title "45 CFR part 164 subpart D — Notification in the Case of Breach of Unsecured Protected Health Information"
     :statute/cfr-title 45
     :statute/cfr-node [["subtitle" "A"] ["subchapter" "C"] ["part" "164"] ["subpart" "D"]]
     :statute/url "https://www.ecfr.gov/current/title-45/subtitle-A/subchapter-C/part-164/subpart-D"
     :statute/verified-label "Notification in the Case of Breach of Unsecured Protected Health Information"
     :statute/verified-via "https://www.ecfr.gov/api/versioner/v1/structure/2026-08-18/title-45.json"
     :statute/verified-at "2026-08-18"
     :statute/status :operative
     :statute/note "Security vs Privacy vs Breach are separate subparts under the same part."}
    {:statute/id :hipaa-164-subpart-e
     :statute/hat :hipaa-ocr
     :statute/topic #{:hipaa :hipaa-privacy}
     :statute/title "45 CFR part 164 subpart E — Privacy of Individually Identifiable Health Information"
     :statute/cfr-title 45
     :statute/cfr-node [["subtitle" "A"] ["subchapter" "C"] ["part" "164"] ["subpart" "E"]]
     :statute/url "https://www.ecfr.gov/current/title-45/subtitle-A/subchapter-C/part-164/subpart-E"
     :statute/verified-label "Privacy of Individually Identifiable Health Information"
     :statute/verified-via "https://www.ecfr.gov/api/versioner/v1/structure/2026-08-18/title-45.json"
     :statute/verified-at "2026-08-18"
     :statute/status :operative
     :statute/note "Security vs Privacy vs Breach are separate subparts under the same part."}
    {:statute/id :hipaa-160-103-definitions
     :statute/hat :hipaa-ocr
     :statute/topic #{:hipaa :definitions}
     :statute/title "45 CFR 160.103 — Definitions"
     :statute/cfr-title 45
     :statute/cfr-node [["subtitle" "A"] ["subchapter" "C"] ["part" "160"] ["subpart" "A"] ["section" "160.103"]]
     :statute/url "https://www.ecfr.gov/current/title-45/subtitle-A/subchapter-C/part-160/subpart-A/section-160.103"
     :statute/verified-label "Definitions."
     :statute/verified-via "https://www.ecfr.gov/api/versioner/v1/structure/2026-08-18/title-45.json"
     :statute/verified-at "2026-08-18"
     :statute/status :operative
     :statute/quote-part "160"
     :statute/quote-section "160.103"
     :statute/verified-quotes ["Except as otherwise provided, the following definitions apply to this subchapter:" "Administrative simplification provision means any requirement or prohibition established by:"]
     :statute/note "Definitions for the HIPAA Administrative Simplification subchapter."}
    {:statute/id :hipaa-164-502-uses
     :statute/hat :hipaa-ocr
     :statute/topic #{:hipaa :privacy :disclosure}
     :statute/title "45 CFR 164.502 — Uses and disclosures of PHI: General rules"
     :statute/cfr-title 45
     :statute/cfr-node [["subtitle" "A"] ["subchapter" "C"] ["part" "164"] ["subpart" "E"] ["section" "164.502"]]
     :statute/url "https://www.ecfr.gov/current/title-45/subtitle-A/subchapter-C/part-164/subpart-E/section-164.502"
     :statute/verified-label "Uses and disclosures of protected health information: General rules."
     :statute/verified-via "https://www.ecfr.gov/api/versioner/v1/structure/2026-08-18/title-45.json"
     :statute/verified-at "2026-08-18"
     :statute/status :operative
     :statute/quote-part "164"
     :statute/quote-section "164.502"
     :statute/verified-quotes ["A covered entity or business associate may not use or disclose protected health information, except as permitted or required by this subpart or by subpart C of part 160 of this subchapter." "A covered entity must comply with the requirements of this subpart with respect to the protected health information of a deceased individual for a period of 50 years following the death of the individual."]
     :statute/note "Privacy Rule general rule for uses and disclosures of protected health information."}
    {:statute/id :hipaa-164-306-security
     :statute/hat :hipaa-ocr
     :statute/topic #{:hipaa :security}
     :statute/title "45 CFR 164.306 — Security standards: General rules"
     :statute/cfr-title 45
     :statute/cfr-node [["subtitle" "A"] ["subchapter" "C"] ["part" "164"] ["subpart" "C"] ["section" "164.306"]]
     :statute/url "https://www.ecfr.gov/current/title-45/subtitle-A/subchapter-C/part-164/subpart-C/section-164.306"
     :statute/verified-label "Security standards: General rules."
     :statute/verified-via "https://www.ecfr.gov/api/versioner/v1/structure/2026-08-18/title-45.json"
     :statute/verified-at "2026-08-18"
     :statute/status :operative
     :statute/quote-part "164"
     :statute/quote-section "164.306"
     :statute/verified-quotes ["Ensure the confidentiality, integrity, and availability of all electronic protected health information the covered entity or business associate creates, receives, maintains, or transmits."]
     :statute/note "Security Rule general requirements for electronic PHI."}
    {:statute/id :hit-part-170
     :statute/hat :sibling-hhs
     :statute/topic #{:hit :onc}
     :statute/title "45 CFR part 170 — Health IT Standards and Certification"
     :statute/cfr-title 45
     :statute/cfr-node [["subtitle" "A"] ["subchapter" "D"] ["part" "170"]]
     :statute/url "https://www.ecfr.gov/current/title-45/subtitle-A/subchapter-D/part-170"
     :statute/verified-label "Health Information Technology Standards, Implementation Specifications, and Certification Criteria and Certification Programs for Health Information Technology"
     :statute/verified-via "https://www.ecfr.gov/api/versioner/v1/structure/2026-08-18/title-45.json"
     :statute/verified-at "2026-08-18"
     :statute/status :operative
     :statute/note "ONC health IT certification criteria. Certification is not HIPAA Privacy/Security compliance."}
    {:statute/id :human-subjects-part-46
     :statute/hat :sibling-hhs
     :statute/topic #{:human-subjects :common-rule}
     :statute/title "45 CFR part 46 — Protection of Human Subjects"
     :statute/cfr-title 45
     :statute/cfr-node [["subtitle" "A"] ["subchapter" "A"] ["part" "46"]]
     :statute/url "https://www.ecfr.gov/current/title-45/subtitle-A/subchapter-A/part-46"
     :statute/verified-label "Protection of Human Subjects"
     :statute/verified-via "https://www.ecfr.gov/api/versioner/v1/structure/2026-08-18/title-45.json"
     :statute/verified-at "2026-08-18"
     :statute/status :operative
     :statute/note "Common Rule / IRB protections. Research ethics is not HIPAA Administrative Simplification."}
    {:statute/id :cms-chapter-iv
     :statute/hat :cms-program
     :statute/topic #{:cms :medicare-medicaid}
     :statute/title "42 CFR chapter IV — CMS"
     :statute/cfr-title 42
     :statute/cfr-node [["chapter" "IV"]]
     :statute/url "https://www.ecfr.gov/current/title-42/chapter-IV"
     :statute/verified-label "Centers for Medicare &amp; Medicaid Services, Department of Health and Human Services"
     :statute/verified-via "https://www.ecfr.gov/api/versioner/v1/structure/2026-08-18/title-42.json"
     :statute/verified-at "2026-08-18"
     :statute/status :operative
     :statute/note "Centers for Medicare & Medicaid Services chapter. Payment and participation — not HIPAA Admin Simplification."}
    {:statute/id :cms-part-400
     :statute/hat :cms-program
     :statute/topic #{:cms}
     :statute/title "42 CFR part 400"
     :statute/cfr-title 42
     :statute/cfr-node [["chapter" "IV"] ["subchapter" "A"] ["part" "400"]]
     :statute/url "https://www.ecfr.gov/current/title-42/chapter-IV/subchapter-A/part-400"
     :statute/verified-label "Introduction; Definitions"
     :statute/verified-via "https://www.ecfr.gov/api/versioner/v1/structure/2026-08-18/title-42.json"
     :statute/verified-at "2026-08-18"
     :statute/status :operative
     :statute/note "CMS introduction and definitions for chapter IV."}
    {:statute/id :cms-part-401
     :statute/hat :cms-program
     :statute/topic #{:cms}
     :statute/title "42 CFR part 401"
     :statute/cfr-title 42
     :statute/cfr-node [["chapter" "IV"] ["subchapter" "A"] ["part" "401"]]
     :statute/url "https://www.ecfr.gov/current/title-42/chapter-IV/subchapter-A/part-401"
     :statute/verified-label "General Administrative Requirements"
     :statute/verified-via "https://www.ecfr.gov/api/versioner/v1/structure/2026-08-18/title-42.json"
     :statute/verified-at "2026-08-18"
     :statute/status :operative
     :statute/note "CMS general administrative requirements."}
    {:statute/id :cms-part-402
     :statute/hat :cms-program
     :statute/topic #{:cms}
     :statute/title "42 CFR part 402"
     :statute/cfr-title 42
     :statute/cfr-node [["chapter" "IV"] ["subchapter" "A"] ["part" "402"]]
     :statute/url "https://www.ecfr.gov/current/title-42/chapter-IV/subchapter-A/part-402"
     :statute/verified-label "Civil Money Penalties, Assessments, and Exclusions"
     :statute/verified-via "https://www.ecfr.gov/api/versioner/v1/structure/2026-08-18/title-42.json"
     :statute/verified-at "2026-08-18"
     :statute/status :operative
     :statute/note "CMS civil money penalties, assessments, and exclusions."}
    {:statute/id :cms-part-405
     :statute/hat :cms-program
     :statute/topic #{:cms}
     :statute/title "42 CFR part 405"
     :statute/cfr-title 42
     :statute/cfr-node [["chapter" "IV"] ["subchapter" "B"] ["part" "405"]]
     :statute/url "https://www.ecfr.gov/current/title-42/chapter-IV/subchapter-B/part-405"
     :statute/verified-label "Federal Health Insurance for the Aged and Disabled"
     :statute/verified-via "https://www.ecfr.gov/api/versioner/v1/structure/2026-08-18/title-42.json"
     :statute/verified-at "2026-08-18"
     :statute/status :operative
     :statute/note "Federal health insurance for the aged and disabled (Medicare)."}
    {:statute/id :cms-part-411
     :statute/hat :cms-program
     :statute/topic #{:cms}
     :statute/title "42 CFR part 411"
     :statute/cfr-title 42
     :statute/cfr-node [["chapter" "IV"] ["subchapter" "B"] ["part" "411"]]
     :statute/url "https://www.ecfr.gov/current/title-42/chapter-IV/subchapter-B/part-411"
     :statute/verified-label "Exclusions from Medicare and Limitations on Medicare Payment"
     :statute/verified-via "https://www.ecfr.gov/api/versioner/v1/structure/2026-08-18/title-42.json"
     :statute/verified-at "2026-08-18"
     :statute/status :operative
     :statute/note "Exclusions from Medicare and limitations on Medicare payment."}
    {:statute/id :cms-part-412
     :statute/hat :cms-program
     :statute/topic #{:cms}
     :statute/title "42 CFR part 412"
     :statute/cfr-title 42
     :statute/cfr-node [["chapter" "IV"] ["subchapter" "B"] ["part" "412"]]
     :statute/url "https://www.ecfr.gov/current/title-42/chapter-IV/subchapter-B/part-412"
     :statute/verified-label "Prospective Payment Systems for Inpatient Hospital Services"
     :statute/verified-via "https://www.ecfr.gov/api/versioner/v1/structure/2026-08-18/title-42.json"
     :statute/verified-at "2026-08-18"
     :statute/status :operative
     :statute/note "Prospective payment systems for inpatient hospital services."}
    {:statute/id :cms-part-413
     :statute/hat :cms-program
     :statute/topic #{:cms}
     :statute/title "42 CFR part 413"
     :statute/cfr-title 42
     :statute/cfr-node [["chapter" "IV"] ["subchapter" "B"] ["part" "413"]]
     :statute/url "https://www.ecfr.gov/current/title-42/chapter-IV/subchapter-B/part-413"
     :statute/verified-label "Principles of Reasonable Cost Reimbursement; Payment for End-Stage Renal Disease Services; Prospectively Determined Payment Rates for Skilled Nursing Facilities; Payment for Acute Kidney Injury Dialysis"
     :statute/verified-via "https://www.ecfr.gov/api/versioner/v1/structure/2026-08-18/title-42.json"
     :statute/verified-at "2026-08-18"
     :statute/status :operative
     :statute/note "Principles of reasonable cost reimbursement; payment for end-stage renal disease services."}
    {:statute/id :cms-part-414
     :statute/hat :cms-program
     :statute/topic #{:cms}
     :statute/title "42 CFR part 414"
     :statute/cfr-title 42
     :statute/cfr-node [["chapter" "IV"] ["subchapter" "B"] ["part" "414"]]
     :statute/url "https://www.ecfr.gov/current/title-42/chapter-IV/subchapter-B/part-414"
     :statute/verified-label "Payment for Part B Medical and Other Health Services"
     :statute/verified-via "https://www.ecfr.gov/api/versioner/v1/structure/2026-08-18/title-42.json"
     :statute/verified-at "2026-08-18"
     :statute/status :operative
     :statute/note "Payment for Part B medical and other health services."}
    {:statute/id :cms-part-418
     :statute/hat :cms-program
     :statute/topic #{:cms}
     :statute/title "42 CFR part 418"
     :statute/cfr-title 42
     :statute/cfr-node [["chapter" "IV"] ["subchapter" "B"] ["part" "418"]]
     :statute/url "https://www.ecfr.gov/current/title-42/chapter-IV/subchapter-B/part-418"
     :statute/verified-label "Hospice Care"
     :statute/verified-via "https://www.ecfr.gov/api/versioner/v1/structure/2026-08-18/title-42.json"
     :statute/verified-at "2026-08-18"
     :statute/status :operative
     :statute/note "Hospice care."}
    {:statute/id :cms-part-482
     :statute/hat :cms-program
     :statute/topic #{:cms}
     :statute/title "42 CFR part 482"
     :statute/cfr-title 42
     :statute/cfr-node [["chapter" "IV"] ["subchapter" "G"] ["part" "482"]]
     :statute/url "https://www.ecfr.gov/current/title-42/chapter-IV/subchapter-G/part-482"
     :statute/verified-label "Conditions of Participation for Hospitals"
     :statute/verified-via "https://www.ecfr.gov/api/versioner/v1/structure/2026-08-18/title-42.json"
     :statute/verified-at "2026-08-18"
     :statute/status :operative
     :statute/note "Conditions of Participation for Hospitals — CMS survey/certification, not OCR HIPAA."}
    {:statute/id :cms-part-422
     :statute/hat :cms-program
     :statute/topic #{:cms}
     :statute/title "42 CFR part 422"
     :statute/cfr-title 42
     :statute/cfr-node [["chapter" "IV"] ["subchapter" "B"] ["part" "422"]]
     :statute/url "https://www.ecfr.gov/current/title-42/chapter-IV/subchapter-B/part-422"
     :statute/verified-label "Medicare Advantage Program"
     :statute/verified-via "https://www.ecfr.gov/api/versioner/v1/structure/2026-08-18/title-42.json"
     :statute/verified-at "2026-08-18"
     :statute/status :operative
     :statute/note "Medicare Advantage Program."}
    {:statute/id :cms-part-423
     :statute/hat :cms-program
     :statute/topic #{:cms}
     :statute/title "42 CFR part 423"
     :statute/cfr-title 42
     :statute/cfr-node [["chapter" "IV"] ["subchapter" "B"] ["part" "423"]]
     :statute/url "https://www.ecfr.gov/current/title-42/chapter-IV/subchapter-B/part-423"
     :statute/verified-label "Voluntary Medicare Prescription Drug Benefit"
     :statute/verified-via "https://www.ecfr.gov/api/versioner/v1/structure/2026-08-18/title-42.json"
     :statute/verified-at "2026-08-18"
     :statute/status :operative
     :statute/note "Voluntary Medicare Prescription Drug Benefit."}
    {:statute/id :part2-sud-records
     :statute/hat :part2-contrast
     :statute/topic #{:part2 :sud :confidentiality}
     :statute/title "42 CFR part 2 — Confidentiality of Substance Use Disorder Patient Records"
     :statute/cfr-title 42
     :statute/cfr-node [["chapter" "I"] ["subchapter" "A"] ["part" "2"]]
     :statute/url "https://www.ecfr.gov/current/title-42/chapter-I/subchapter-A/part-2"
     :statute/verified-label "Confidentiality of Substance Use Disorder Patient Records"
     :statute/verified-via "https://www.ecfr.gov/api/versioner/v1/structure/2026-08-18/title-42.json"
     :statute/verified-at "2026-08-18"
     :statute/status :operative
     :statute/note "Part 2 is NOT the HIPAA Privacy Rule. Separate SUD confidentiality under 42 U.S.C. 290dd-2."}
    {:statute/id :part2-2-2-purpose
     :statute/hat :part2-contrast
     :statute/topic #{:part2}
     :statute/title "42 CFR 2.2 — Purpose and effect"
     :statute/cfr-title 42
     :statute/cfr-node [["chapter" "I"] ["subchapter" "A"] ["part" "2"] ["subpart" "A"] ["section" "2.2"]]
     :statute/url "https://www.ecfr.gov/current/title-42/chapter-I/subchapter-A/part-2/subpart-A/section-2.2"
     :statute/verified-label "Purpose and effect."
     :statute/verified-via "https://www.ecfr.gov/api/versioner/v1/structure/2026-08-18/title-42.json"
     :statute/verified-at "2026-08-18"
     :statute/status :operative
     :statute/quote-part "2"
     :statute/quote-section "2.2"
     :statute/verified-quotes ["Pursuant to 42 U.S.C. 290dd-2(g), the regulations in this part impose restrictions upon the use and disclosure of substance use disorder patient records"]
     :statute/note "States Part 2 purpose for SUD patient-record restrictions."}
    {:statute/id :part2-2-11
     :statute/hat :part2-contrast
     :statute/topic #{:part2}
     :statute/title "42 CFR 2.11"
     :statute/cfr-title 42
     :statute/cfr-node [["chapter" "I"] ["subchapter" "A"] ["part" "2"] ["subpart" "B"] ["section" "2.11"]]
     :statute/url "https://www.ecfr.gov/current/title-42/chapter-I/subchapter-A/part-2/subpart-B/section-2.11"
     :statute/verified-label "Definitions."
     :statute/verified-via "https://www.ecfr.gov/api/versioner/v1/structure/2026-08-18/title-42.json"
     :statute/verified-at "2026-08-18"
     :statute/status :operative
     :statute/note "Part 2 operative section — still not HIPAA."}
    {:statute/id :part2-2-12
     :statute/hat :part2-contrast
     :statute/topic #{:part2}
     :statute/title "42 CFR 2.12"
     :statute/cfr-title 42
     :statute/cfr-node [["chapter" "I"] ["subchapter" "A"] ["part" "2"] ["subpart" "B"] ["section" "2.12"]]
     :statute/url "https://www.ecfr.gov/current/title-42/chapter-I/subchapter-A/part-2/subpart-B/section-2.12"
     :statute/verified-label "Applicability."
     :statute/verified-via "https://www.ecfr.gov/api/versioner/v1/structure/2026-08-18/title-42.json"
     :statute/verified-at "2026-08-18"
     :statute/status :operative
     :statute/note "Part 2 operative section — still not HIPAA."}
    {:statute/id :part2-2-13
     :statute/hat :part2-contrast
     :statute/topic #{:part2}
     :statute/title "42 CFR 2.13"
     :statute/cfr-title 42
     :statute/cfr-node [["chapter" "I"] ["subchapter" "A"] ["part" "2"] ["subpart" "B"] ["section" "2.13"]]
     :statute/url "https://www.ecfr.gov/current/title-42/chapter-I/subchapter-A/part-2/subpart-B/section-2.13"
     :statute/verified-label "Confidentiality restrictions and safeguards."
     :statute/verified-via "https://www.ecfr.gov/api/versioner/v1/structure/2026-08-18/title-42.json"
     :statute/verified-at "2026-08-18"
     :statute/status :operative
     :statute/note "Part 2 operative section — still not HIPAA."}
    {:statute/id :part2-2-31
     :statute/hat :part2-contrast
     :statute/topic #{:part2}
     :statute/title "42 CFR 2.31"
     :statute/cfr-title 42
     :statute/cfr-node [["chapter" "I"] ["subchapter" "A"] ["part" "2"] ["subpart" "C"] ["section" "2.31"]]
     :statute/url "https://www.ecfr.gov/current/title-42/chapter-I/subchapter-A/part-2/subpart-C/section-2.31"
     :statute/verified-label "Consent requirements."
     :statute/verified-via "https://www.ecfr.gov/api/versioner/v1/structure/2026-08-18/title-42.json"
     :statute/verified-at "2026-08-18"
     :statute/status :operative
     :statute/note "Part 2 operative section — still not HIPAA."}
    {:statute/id :ug-part-200
     :statute/hat :grants-baseline
     :statute/topic #{:grants :uniform-guidance}
     :statute/title "2 CFR part 200 — Uniform Administrative Requirements, Cost Principles, and Audit Requirements"
     :statute/cfr-title 2
     :statute/cfr-node [["subtitle" "A"] ["chapter" "II"] ["part" "200"]]
     :statute/url "https://www.ecfr.gov/current/title-2/subtitle-A/chapter-II/part-200"
     :statute/verified-label "Uniform Administrative Requirements, Cost Principles, and Audit Requirements for Federal Awards"
     :statute/verified-via "https://www.ecfr.gov/api/versioner/v1/structure/2026-08-18/title-2.json"
     :statute/verified-at "2026-08-18"
     :statute/status :operative
     :statute/note "Uniform Guidance for Federal awards. Award eligibility lives here — not in 45 CFR HIPAA parts."}
    {:statute/id :ug-200-subpart-a
     :statute/hat :grants-baseline
     :statute/topic #{:grants}
     :statute/title "2 CFR part 200 subpart A — Acronyms and Definitions"
     :statute/cfr-title 2
     :statute/cfr-node [["subtitle" "A"] ["chapter" "II"] ["part" "200"] ["subpart" "A"]]
     :statute/url "https://www.ecfr.gov/current/title-2/subtitle-A/chapter-II/part-200/subpart-A"
     :statute/verified-label "Acronyms and Definitions"
     :statute/verified-via "https://www.ecfr.gov/api/versioner/v1/structure/2026-08-18/title-2.json"
     :statute/verified-at "2026-08-18"
     :statute/status :operative
     :statute/note "Uniform Guidance subpart. HHS award administration cites these; HIPAA does not replace them."}
    {:statute/id :ug-200-subpart-b
     :statute/hat :grants-baseline
     :statute/topic #{:grants}
     :statute/title "2 CFR part 200 subpart B — General Provisions"
     :statute/cfr-title 2
     :statute/cfr-node [["subtitle" "A"] ["chapter" "II"] ["part" "200"] ["subpart" "B"]]
     :statute/url "https://www.ecfr.gov/current/title-2/subtitle-A/chapter-II/part-200/subpart-B"
     :statute/verified-label "General Provisions"
     :statute/verified-via "https://www.ecfr.gov/api/versioner/v1/structure/2026-08-18/title-2.json"
     :statute/verified-at "2026-08-18"
     :statute/status :operative
     :statute/note "Uniform Guidance subpart. HHS award administration cites these; HIPAA does not replace them."}
    {:statute/id :ug-200-subpart-c
     :statute/hat :grants-baseline
     :statute/topic #{:grants}
     :statute/title "2 CFR part 200 subpart C — Pre-Federal Award Requirements and Contents of Federal Awards"
     :statute/cfr-title 2
     :statute/cfr-node [["subtitle" "A"] ["chapter" "II"] ["part" "200"] ["subpart" "C"]]
     :statute/url "https://www.ecfr.gov/current/title-2/subtitle-A/chapter-II/part-200/subpart-C"
     :statute/verified-label "Pre-Federal Award Requirements and Contents of Federal Awards"
     :statute/verified-via "https://www.ecfr.gov/api/versioner/v1/structure/2026-08-18/title-2.json"
     :statute/verified-at "2026-08-18"
     :statute/status :operative
     :statute/note "Uniform Guidance subpart. HHS award administration cites these; HIPAA does not replace them."}
    {:statute/id :ug-200-subpart-d
     :statute/hat :grants-baseline
     :statute/topic #{:grants}
     :statute/title "2 CFR part 200 subpart D — Post Federal Award Requirements"
     :statute/cfr-title 2
     :statute/cfr-node [["subtitle" "A"] ["chapter" "II"] ["part" "200"] ["subpart" "D"]]
     :statute/url "https://www.ecfr.gov/current/title-2/subtitle-A/chapter-II/part-200/subpart-D"
     :statute/verified-label "Post Federal Award Requirements"
     :statute/verified-via "https://www.ecfr.gov/api/versioner/v1/structure/2026-08-18/title-2.json"
     :statute/verified-at "2026-08-18"
     :statute/status :operative
     :statute/note "Uniform Guidance subpart. HHS award administration cites these; HIPAA does not replace them."}
    {:statute/id :ug-200-subpart-e
     :statute/hat :grants-baseline
     :statute/topic #{:grants}
     :statute/title "2 CFR part 200 subpart E — Cost Principles"
     :statute/cfr-title 2
     :statute/cfr-node [["subtitle" "A"] ["chapter" "II"] ["part" "200"] ["subpart" "E"]]
     :statute/url "https://www.ecfr.gov/current/title-2/subtitle-A/chapter-II/part-200/subpart-E"
     :statute/verified-label "Cost Principles"
     :statute/verified-via "https://www.ecfr.gov/api/versioner/v1/structure/2026-08-18/title-2.json"
     :statute/verified-at "2026-08-18"
     :statute/status :operative
     :statute/note "Uniform Guidance subpart. HHS award administration cites these; HIPAA does not replace them."}
    {:statute/id :ug-200-subpart-f
     :statute/hat :grants-baseline
     :statute/topic #{:grants}
     :statute/title "2 CFR part 200 subpart F — Audit Requirements"
     :statute/cfr-title 2
     :statute/cfr-node [["subtitle" "A"] ["chapter" "II"] ["part" "200"] ["subpart" "F"]]
     :statute/url "https://www.ecfr.gov/current/title-2/subtitle-A/chapter-II/part-200/subpart-F"
     :statute/verified-label "Audit Requirements"
     :statute/verified-via "https://www.ecfr.gov/api/versioner/v1/structure/2026-08-18/title-2.json"
     :statute/verified-at "2026-08-18"
     :statute/status :operative
     :statute/note "Uniform Guidance subpart. HHS award administration cites these; HIPAA does not replace them."}
    {:statute/id :ug-200-100-purpose
     :statute/hat :grants-baseline
     :statute/topic #{:grants}
     :statute/title "2 CFR 200.100 — Purpose"
     :statute/cfr-title 2
     :statute/cfr-node [["subtitle" "A"] ["chapter" "II"] ["part" "200"] ["subpart" "B"] ["section" "200.100"]]
     :statute/url "https://www.ecfr.gov/current/title-2/subtitle-A/chapter-II/part-200/subpart-B/section-200.100"
     :statute/verified-label "Purpose."
     :statute/verified-via "https://www.ecfr.gov/api/versioner/v1/structure/2026-08-18/title-2.json"
     :statute/verified-at "2026-08-18"
     :statute/status :operative
     :statute/quote-part "200"
     :statute/quote-section "200.100"
     :statute/verified-quotes ["This part establishes uniform administrative requirements, cost principles, and audit requirements for Federal awards."]
     :statute/note "States that part 200 establishes uniform administrative requirements for Federal awards."}
]})

(def absences
  "Controlled negatives that pin the HHS trap. Shape matches the live gate:
  absent/control patterns are nested maps, not bare strings."
  [{:absence/id :no-cms-payment-vocabulary-in-hipaa-parts
    :absence/claim
    "45 CFR part 164 (HIPAA Security and Privacy) does not carry CMS Conditions
     of Participation vocabulary as a node label. HIPAA Administrative
     Simplification is not a hospital CoP survey checklist; CoP lives in 42 CFR
     part 482 under CMS chapter IV."
    :absence/absent-label
    {:statute/cfr-title 45
     :statute/under [["subtitle" "A"] ["subchapter" "C"] ["part" "164"]]
     :statute/pattern "(?i)conditions of participation"}
    :absence/control-label
    {:statute/pattern "(?i)Security and Privacy|protected health information|electronic protected health information"
     :absence/control-note
     "Part 164 itself is Security and Privacy. If this control stops matching,
      the scan is not reading the HIPAA part."}
    :absence/see-instead
    {:statute/id :cms-part-482
     :statute/title "42 CFR part 482 — Conditions of Participation for Hospitals"
     :statute/cfr-title 42
     :statute/cfr-node [["chapter" "IV"] ["subchapter" "G"] ["part" "482"]]
     :statute/url "https://www.ecfr.gov/current/title-42/chapter-IV/subchapter-G/part-482"
     :statute/verified-label "Conditions of Participation for Hospitals"
     :statute/verified-via "https://www.ecfr.gov/api/versioner/v1/structure/2026-08-18/title-42.json"
     :statute/verified-at "2026-08-18"}}

   {:absence/id :no-uniform-guidance-in-hipaa-part-160
    :absence/claim
    "45 CFR part 160 does not host a node labelled with the Uniform Guidance
     title (Uniform Administrative Requirements, Cost Principles, and Audit
     Requirements). Award administration for Federal awards lives in 2 CFR part
     200; HIPAA Administrative Simplification does not replace it."
    :absence/absent-label
    {:statute/cfr-title 45
     :statute/under [["subtitle" "A"] ["subchapter" "C"] ["part" "160"]]
     :statute/pattern "(?i)uniform administrative requirements, cost principles, and audit requirements"}
    :absence/control-label
    {:statute/pattern "(?i)General Administrative Requirements|Administrative Simplification"
     :absence/control-note
     "Part 160 is General Administrative Requirements. If this control stops
      matching, the walk reached the wrong subtree."}
    :absence/see-instead
    {:statute/id :ug-part-200
     :statute/title "2 CFR part 200"
     :statute/cfr-title 2
     :statute/cfr-node [["subtitle" "A"] ["chapter" "II"] ["part" "200"]]
     :statute/url "https://www.ecfr.gov/current/title-2/subtitle-A/chapter-II/part-200"
     :statute/verified-label "Uniform Administrative Requirements, Cost Principles, and Audit Requirements for Federal Awards"
     :statute/verified-via "https://www.ecfr.gov/api/versioner/v1/structure/2026-08-18/title-2.json"
     :statute/verified-at "2026-08-18"}}

   {:absence/id :no-part2-sud-label-in-hipaa-privacy-subpart
    :absence/claim
    "45 CFR part 164 subpart E (HIPAA Privacy) is not 42 CFR part 2. Scanning
     the Privacy subpart for Part 2's substance-use-disorder confidentiality
     title must not succeed; the regimes share health-data subject matter and
     almost none of the operative citation path."
    :absence/absent-label
    {:statute/cfr-title 45
     :statute/under [["subtitle" "A"] ["subchapter" "C"] ["part" "164"] ["subpart" "E"]]
     :statute/pattern "(?i)substance use disorder patient records"}
    :absence/control-label
    {:statute/pattern "(?i)Privacy of Individually Identifiable Health Information|protected health information"
     :absence/control-note
     "Subpart E itself is Privacy of Individually Identifiable Health
      Information. If this control stops matching, the scan missed Privacy."}
    :absence/see-instead
    {:statute/id :part2-sud-records
     :statute/title "42 CFR part 2"
     :statute/cfr-title 42
     :statute/cfr-node [["chapter" "I"] ["subchapter" "A"] ["part" "2"]]
     :statute/url "https://www.ecfr.gov/current/title-42/chapter-I/subchapter-A/part-2"
     :statute/verified-label "Confidentiality of Substance Use Disorder Patient Records"
     :statute/verified-via "https://www.ecfr.gov/api/versioner/v1/structure/2026-08-18/title-42.json"
     :statute/verified-at "2026-08-18"}}

   {:absence/id :no-hipaa-privacy-rule-title-in-part2
    :absence/claim
    "42 CFR part 2 does not retitle itself as the HIPAA Privacy Rule. An
     operator who treats Part 2 confidentiality as HIPAA Privacy subpart E has
     opened the wrong book for the wrong question."
    :absence/absent-label
    {:statute/cfr-title 42
     :statute/under [["chapter" "I"] ["subchapter" "A"] ["part" "2"]]
     :statute/pattern "(?i)privacy of individually identifiable health information"}
    :absence/control-label
    {:statute/pattern "(?i)Confidentiality of Substance Use Disorder Patient Records|substance use disorder"
     :absence/control-note
     "Part 2 itself carries the SUD confidentiality title. If this control
      stops matching, the walk is not inside Part 2."}
    :absence/see-instead
    {:statute/id :hipaa-164-subpart-e
     :statute/title "45 CFR part 164 subpart E"
     :statute/cfr-title 45
     :statute/cfr-node [["subtitle" "A"] ["subchapter" "C"] ["part" "164"] ["subpart" "E"]]
     :statute/url "https://www.ecfr.gov/current/title-45/subtitle-A/subchapter-C/part-164/subpart-E"
     :statute/verified-label "Privacy of Individually Identifiable Health Information"
     :statute/verified-via "https://www.ecfr.gov/api/versioner/v1/structure/2026-08-18/title-45.json"
     :statute/verified-at "2026-08-18"}}
   ])

(defn entries [] (get catalog "USA-HHS"))
(defn by-hat [hat] (filterv #(= hat (:statute/hat %)) (entries)))
(defn by-topic [topic] (filterv #(contains? (:statute/topic %) topic) (entries)))
(defn titles-covered [] (->> (entries) (map :statute/cfr-title) distinct sort vec))
(defn quoted-entries [] (filterv #(seq (:statute/verified-quotes %)) (entries)))
(defn quote-count [] (reduce + 0 (map #(count (:statute/verified-quotes %)) (quoted-entries))))
(defn summary []
  {:iso "USA-HHS"
   :entries (count (entries))
   :titles (titles-covered)
   :quotes (quote-count)
   :absences (count absences)})
