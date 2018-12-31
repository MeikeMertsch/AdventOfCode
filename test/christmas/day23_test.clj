(ns christmas.day23-test
 (:require [christmas.day23 :as chr]
  		   [expectations :refer :all]
  		   [clojure.string :as str]))


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

(def file (get-file "resources/day21-25/Input23"))
(def file-b (get-sorted-file "resources/day21-25/Input23b"))
(def real-file (get-sorted-file "resources/day21-25/Input23real"))

(expect [[0 0 0] 4] (chr/find-strongest file))

(expect '(5 1 3 2 5 4 3 4 0) (chr/all-distances [0 0 0] (map first file)))

(expect 4 (chr/distance [0 0 0][0 4 0]))

(expect 7 (chr/day23 file))
;(expect 172 (chr/day23 real-file))


;----------------------------------------


(expect [[0 4][0 5][0 3]] (chr/dimensions file))
(expect [[-162535315 239479797] 
		 [-41107412 118057119] 
		 [-109618347 132080574]] (chr/dimensions real-file))

(def a [[10 12 12] 2])
(def b [[12 14 12] 2])
(def c [[16 12 12] 4])
(def d [[14 14 14] 6])
(def e [[50 50 50] 200])
(def f [[10 10 10] 5])

(expect [[12 12 12][13 13 12][14 14 12]] (chr/single-overlap b c))

(expect [0 1 2] (chr/c-range [12 14] 12))
(expect [-4 -3 -2] (chr/c-range [12 14] 16))

(expect [[12 14] [12 16] [10 14]] (chr/cube b c))

(expect [[12 14 10][12 13 11][12 15 11][13 14 11][12 12 12][12 16 12][13 13 12][13 15 12][14 14 12][12 13 13][12 15 13][13 14 13][12 14 14]]
 (chr/shell-coords (first b) (last b) (chr/cube b c)))

(expect [[0 -2 0][0 2 0] 
		 [1 -1 0][1 1 0]
		 [2  0 0]] 
		 (chr/plane-coords 2 [0 1 2] [-2 -1 0 1 2] 0))

(expect [[0 0 -2]] 
		 (chr/plane-coords 0 [0 1 2] [-2 -1 0 1 2] -2))

(expect #{[0 4][1 3][2 2]} (into #{} (chr/radius-pairs (chr/distance [10 12 12][10 10 10]) 2 5)))
(expect [[2 4]] (chr/radius-pairs (chr/distance (first b) (first c)) (last b) (last c)))

;(expect "" (chr/all-overlaps a f))
;(expect "" real-file)
(comment 

(expect "" (chr/get-all-overlaps [[] (vector a f)]))

(expect "" (chr/get-all-overlaps [[] [a f]]))
)
;(expect-focused "" (chr/day23b real-file))

(expect true (chr/overlaps? a b))
(expect false (chr/overlaps? d f))

(expect 6 (chr/overlaps a file-b))
(expect 3 (chr/overlaps f file-b))

(expect [4 0 0 0 86 3] (map last (chr/differences file-b a)))
(expect [3 -1 -1 -1 85 10] (map last (chr/differences file-b f)))

;(expect 4 (chr/day23d file-b))
;(expect-focused 3 (chr/day23d real-file))

;(expect 989 (chr/day23c real-file))

;(expect "" (chr/get-all-overlaps [[] [(first real-file) (second real-file)]]))











