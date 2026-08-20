# cloud-itonami-iso3166-usa-hhs

Open ISO 3166 **agency-level** Blueprint for **USA-HHS**: Department of Health
and Human Services (parent country: **USA**).

This leaf designs a forkable OSS business for an independent operator
navigating **HHS**-specific regulatory compliance — award eligibility under
Uniform Guidance and HIPAA-adjacent health-data readiness — composing with the
country coordinator `cloud-itonami-iso3166-usa`.

## What this is NOT

- **Not HHS.** Commercial compliance navigation only.
- **Not legal advice.** Cite official sources; route licensed work to counsel.
- **Not one book.** HIPAA Privacy/Security (45 CFR parts 160/162/164, OCR) is
  not CMS program/payment rules (42 CFR chapter IV), is not 42 CFR part 2 (SUD
  confidentiality), and is not Uniform Guidance for Federal awards (2 CFR part
  200). The catalog below exists to keep those four from collapsing into one
  “HHS paperwork” story.

## The verified catalog

`src/statute/facts.cljc` is the spec-basis: **39 regulatory anchors across three
CFR titles (45, 42, 2), 7 byte-exact quotes of live regulation text, and 4
checked absences.** Every heading is the byte-exact `label_description`
returned by the official eCFR versioner API, and every quote is a byte-exact
span of the section text returned by the same API, both pinned to the
`2026-08-18` snapshot.

```bash
nbb tools/verify_citations.cljs     # live gate: re-fetches eCFR, exits 0/1/2
clojure -M:test                     # offline invariants
clojure -M:lint
```

| exit | meaning |
|---|---|
| 0 | answered; every heading, quote and absence checked out, floors met |
| 1 | answered; something drifted — the message names which entry |
| 2 | **could not answer** — network, undeclared endpoint, broken control, or below floor |

Top-level `:deps` is empty on purpose (ADR-2608201300): the catalog is plain
data; lint/test tool coords stay under aliases only.

## Official surface

- https://www.hhs.gov/
- https://www.hhs.gov/hipaa/

## Capability layer

Resolves via `kotoba-lang/iso3166` (`USA-HHS`, parent `USA`).

## License

AGPL-3.0-or-later.
