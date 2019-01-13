(ns christmas.chr18.day25-test
 (:require [christmas.chr18.day25 :as chr]
  		   [expectations :refer :all]
  		   [clojure.string :as str]))

(comment 
(defn parse-int [thing]
	(Integer/parseInt thing))

(defn get-file [file]
	(->> (slurp file)
		 str/split-lines
		 (map #(re-seq #"-?\d+" %))
		 (map (partial map parse-int))))

(def file-2 (get-file "resources/chr18/Input25_2"))
(def file-3 (get-file "resources/chr18/Input25_3"))
(def file-4 (get-file "resources/chr18/Input25_4"))
(def file-8 (get-file "resources/chr18/Input25_8"))
(def file-real (get-file "resources/chr18/Input25real"))

;(expect "" file-8)

(expect 11 (chr/distance [-2,3,-2,1][0,2,3,-2]))

(expect [] (chr/collect-if-close [-2,3,-2,1][][0,2,3,-2]))
(expect [[[-2,3,-2,1][-2,2,-1,1]]] (chr/collect-if-close [-2,3,-2,1][][-2,2,-1,1]))
(expect [[:a :b][[-2,3,-2,1][-2,2,-1,1]]] (chr/collect-if-close [-2,3,-2,1][[:a :b]][-2,2,-1,1]))

(expect [] (chr/reachables (first file-8) file-8))
(expect [[[-1 -1 1 -2][-1 -2 0 -2]]] (chr/reachables [-1 -1 1 -2] file-8))

(expect 4 (count (chr/all-pairs file-8)))
;(expect 4 (count (chr/all-pairs file-real))) ;2250

(expect [nil nil] (chr/find-constellations [] [[-1 -1 1 -2][-1 -2 0 -2]]))

(expect [#{[-1 -1 1 -2][-1 -2 0 -2]}] (chr/treat-pair [] [[-1 -1 1 -2][-1 -2 0 -2]]))
(expect [#{} #{[-1 -1 1 -2][-1 -2 0 -2]}]  (chr/treat-pair [#{} #{[-1 -1 1 -2][-1 -2 0 -2]}] [[-1 -1 1 -2][-1 -2 0 -2]]))
(expect [#{[-1 -1 1 -2][-1 -3 0 -2][-1 -2 0 -2]} #{}]  (chr/treat-pair [#{} #{[-1 -1 1 -2][-1 -3 0 -2]}] [[-1 -1 1 -2][-1 -2 0 -2]]))
(expect [#{[-1 -2 0 -2][-1 -1 1 -2][-1 -3 0 -2]}]  (chr/treat-pair [#{[-1 -2 0 -2]} #{[-1 -1 1 -2][-1 -3 0 -2]}] [[-1 -1 1 -2][-1 -2 0 -2]]))

(expect 8 (chr/day25 file-8))
(expect 2 (chr/day25 file-2))
(expect 3 (chr/day25 file-3))
(expect 4 (chr/day25 file-4))
;(expect-focused 4 (chr/day25 file-real)) ;386
)
