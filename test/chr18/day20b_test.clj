(ns christmas.day20b-test
 (:require [christmas.day20b :as chr]
  		   [expectations :refer :all]
  		   [clojure.string :as str]
  		   [clojure.zip :as zip]))

(comment
(def file (slurp "resources/day16-20/Input20real"))
(def ex-3 "^WNE$")
(def ex-10 "^ENWWW(NEEE|SSE(EE|N))$")
(def ex-18 "^ENNWSWW(NEWS|)SSSEEN(WNSE|)EE(SWEN|)NNN$")
(def ex-18b "^ENNWSWW(NEWS|)S(SS|W)EEN(WNSE|)EE(SWWWWWEEEEEN|)NNN$")
(def ex-23 "^ESSWWN(E|NNENN(EESS(WNSE|)SSS|WWWSSSSE(SW|NNNE)))$")
(def ex-31 "^WSSEESWWWNW(S|NENNEEEENN(ESSSSW(NWSW|SSEN)|WSWWN(E|WWS(E|SS))))$")
(def ex-31b "WSSEESWWWNW(S|NENNEEEENN(ESSSSW(NWSW|SSEN)|WSWWN(E|WWS(E|SS))))")

(expect [-1 -1] (chr/walk-dir \S [-1 -2]))
(expect [1 2] (chr/walk-dir \W [2 2]))

(expect [[0 -1] {[0 0] 0, [-1 0] 1, [-1 -1] 2, [0 -1] 3}] (chr/parse-direction [[-1 -1] {[0 0] 0, [-1 0] 1, [-1 -1] 2}] \E))
(expect [[0 -1] {[0 0] 0, [0 -1] 1, [-1 -1] 3}] (chr/parse-direction [[-1 -1] {[0 0] 0, [0 -1] 1, [-1 -1] 3}] \E))

(expect [[1 0] {[0 0] 0 [1 0] 1}] (chr/parse-char [[0 0] {[0 0] 0}] \E))
;(expect [[0 0] {[0 0] 0}] (chr/parse-char [[0 0] {[0 0] 0}] \())


;__________________________

(expect ["WSSEESWWWNW" "(" "S" "|" "NENNEEEENN" "(" "ESSSSW" "(" "NWSW" "|" "SSEN" ")" "|" "WSWWN" "(" "E" "|" "WWS" "(" "E" "|" "SS" "))))"] 
	(chr/group-input ex-31))

(expect {[0 0] 0 [1 0] 1 [1 -1] 2 [1 -2] 3 [0 -2] 4 [0 -1] 5 [-1 -1] 6 [-2 -1] 7 }
	(chr/walk-directions 0 [0 0] "ENNWSWW"))

(expect {[0 0] 0 [-1 0] 1 [-1 1] 2 [-1 2] 3 [-1 3] 4}
	(chr/walk-directions 0 [0 0] "WSSSNNNE"))

)
;(expect )
;(expect "" (map (partial apply str) (partition-by #{\( \) \|} (rest (butlast file)))))


