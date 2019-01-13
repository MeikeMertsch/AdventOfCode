(ns christmas.day23b-test
 (:require [christmas.day23b :as chr]
  		   [expectations :refer :all]
  		   [clojure.string :as str]))

(comment
(defn parse-int [thing]
	(Integer/parseInt thing))


(defn get-file [file]
	(->> (slurp file)
		 str/split-lines
		 (map #(re-seq #"-?\d+" %))
		 (map (partial map parse-int))
		 (mapcat #(vector (butlast %) (last %)))
		 (apply hash-map)))

(defn get-sorted-file [file]
	(->> (slurp file)
		 str/split-lines
		 (map #(re-seq #"-?\d+" %))
		 (map (partial map parse-int))
		 (mapcat #(vector (butlast %) (last %)))
		 (partition 2)))

(def file-b (get-sorted-file "resources/day21-25/Input23b"))
(def real-file (get-sorted-file "resources/day21-25/Input23real"))

(def a [[10 12 12] 2])
(def b [[12 14 12] 2])
(def c [[16 12 12] 4])
(def d [[14 14 14] 6])
(def e [[50 50 50] 200])
(def f [[10 10 10] 5])

(expect 4 (chr/distance (first f) (first a)))
(expect (* 36 3) (chr/distance (first d) (first e)))

(expect [[0 12][0 14][0 14]] (chr/dimensions [a]))
(expect [[0 14][0 16][0 14]] (chr/dimensions [a b]))
(expect [[-150 250][-150 250][-150 250]] (chr/dimensions [a b c d e f]))
(expect [[-150 250][-150 250][-150 250]] (chr/dimensions file-b))
;(expect [[-220832598 302761581] [-136942433 212945345] [-201690049 221948599]] (chr/dimensions real-file))

(expect (- 250 -150) (chr/max-side-length [[-150 250][-150 250][-150 250]]))
(expect 10 (chr/max-side-length [[0 3][2 12][15 18]]))
(expect 10 (chr/max-side-length [[0 3][2 11][15 18]]))

(expect 22 (chr/enclosing-octaeder-radius 15))
(expect 313882719 (chr/enclosing-octaeder-radius 221948599))
(expect 428169535 (chr/enclosing-octaeder-radius 302761581))

(expect [2 2 2] (chr/middle [0 0 0] 4))
(expect [3 4 5] (chr/middle [-1 0 1] 8))

(expect [[50 50 50] 566] (chr/init-pos file-b))
(expect [[40964492 124854657 60107041] 740473991] (chr/init-pos real-file))

(expect 5 (chr/count-all-overlaps d file-b))
(expect 3 (chr/count-all-overlaps f file-b))
(expect 1000 (chr/count-all-overlaps (chr/init-pos real-file) real-file))

(expect true (chr/overlaps a b))
(expect false (chr/overlaps d f))

(expect [[7 7 7][7 7 1][7 1 7][7 1 1][1 7 7][1 7 1][1 1 7][1 1 1]] 
	(chr/new-middles 3 [4 4 4]))
)
;(expect "" (chr/break-into-subcubes [[40964492 124854657 60107041] 740473991]))

;(expect "" (chr/day23b file-b))
;(expect "" (chr/day23b real-file))






