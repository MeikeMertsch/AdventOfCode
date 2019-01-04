(ns christmas.day20-test
 (:require [christmas.day20 :as chr]
  		   [expectations :refer :all]
  		   [clojure.string :as str]))

(def file (slurp "resources/day16-20/Input20real"))
(def ex-a "^WNE$")
(def ex-b "^ENWWW(NEEE|SSE(EE|N))$")
(def ex-c "^ENNWSWW(NEWS|)SSSEEN(WNSE|)EE(SWEN|)NNN$")
(def ex-ca "^ENNWSWW(NEWS|)S(SS|W)EEN(WNSE|)EE(SWWWWWEEEEEN|)NNN$")
(def ex-d "^ESSWWN(E|NNENN(EESS(WNSE|)SSS|WWWSSSSE(SW|NNNE)))$")
(def ex-e "^WSSEESWWWNW(S|NENNEEEENN(ESSSSW(NWSW|SSEN)|WSWWN(E|WWS(E|SS))))$")

;(expect "" ex-a)

(expect "" (chr/longest "(WNSE|)"))
(expect "WWENSE" (chr/longest "(WWENSE|SE)"))

(expect "^ENNWSWWSSSEEN(WNSE|)EE(SWEN|)NNN$" (chr/replace-inner "^ENNWSWW(NEWS|)SSSEEN(WNSE|)EE(SWEN|)NNN$"))
(expect "^ENNWSWWSSSEENWWENSEEE(SWEN|)NNN$" (chr/replace-inner "^ENNWSWWSSSEEN(WWENSE|SE)EE(SWEN|)NNN$"))

(expect 3 (chr/day20 ex-a))
(expect 10 (chr/day20 ex-b))
(expect 18 (chr/day20 ex-c))
(expect 23 (chr/day20 ex-d))
(expect 31 (chr/day20 ex-e))
;(expect 21 (chr/day20 ex-ca))
;(expect-focused 0 (chr/day20 file))

;(expect 0 (count (re-seq #"[SWEN]" file))) ; 105 ^ 2