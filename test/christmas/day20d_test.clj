(ns christmas.day20d-test
 (:require [christmas.day20d :as chr]
  		   [expectations :refer :all]
  		   [clojure.string :as str]))

(def file (slurp "resources/day16-20/Input20real"))

(def ex-3 "^WNE$")
(def ex-10 "^ENWWW(NEEE|SSE(EE|N))$")
(def ex-18 "^ENNWSWW(NEWS|)SSSEEN(WNSE|)EE(SWEN|)NNN$")
(def ex-18b "^ENNWSWW(NEWS|)S(SS|W)EEN(WNSE|)EE(SWWWWWEEEEEN|)NNN$")
(def ex-23 "^ESSWWN(E|NNENN(EESS(WNSE|)SSS|WWWSSSSE(SW|NNNE)))$")
(def ex-31 "^WSSEESWWWNW(S|NENNEEEENN(ESSSSW(NWSW|SSEN)|WSWWN(E|WWS(E|SS))))$")

(expect "^WNE$" ex-3)

(expect "SSE(EE|N)" (chr/find-left ex-10))
(expect "ENNWSWW(NEWS|)" (chr/find-left ex-18))

(expect "(NEWS|)SSSEEN" (chr/find-right ex-18))

(expect "ESSSSW(NWSW|SSEN)" (chr/find-left ex-31))

(expect "^ENWWW(NEEE|(SSEEE|SSEN))$" (chr/replace-left ex-10))
(expect "^(ENNWSWWNEWS|ENNWSWW)SSSEEN(WNSE|)EE(SWEN|)NNN$" (chr/replace-left ex-18))
;(expect "^ENWWWNEEE|ENWWWSSEEE|ENWWWSSEN$" (chr/replace-left (chr/replace-left ex-10)))
(expect "^(ENNWSWWNEWS|ENNWSWW)SSSEEN(WNSE|)EE(SWEN|)NNN$" (chr/replace-left ex-18))

;(expect "" (chr/day20 ex-10))
;(expect "" (chr/day20 file)) ;drop 1000 in 1193452 msecs (20 mins) length 2074890

(expect "(AB|A)" (chr/assemble ["A" "B" ""]))