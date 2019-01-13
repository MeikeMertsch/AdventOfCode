(ns christmas.chr18.day19-test
 (:require [christmas.chr18.day19 :as chr]
  		   [expectations :refer :all]
  		   [clojure.string :as str]))

(comment
(defn parse-int [thing]
	(Integer/parseInt thing))


(defn get-file [file]
	(->> (slurp file)
		 str/split-lines
		 (map #(str/split % #" "))
		 (map chr/handle-row)))

(def file (get-file "resources/chr18/Input19"))
(def real-file (get-file "resources/chr18/Input19real"))
(def input 0)
(def real-input 5)

(expect [0, 5, 0, 0, 0, 0] (chr/exec [0, 0, 0, 0, 0, 0] (first file)))
(expect [1, 5, 6, 0, 0, 0] (chr/exec [1, 5, 0, 0, 0, 0] (second file)))


;(expect-focused [0, 5, 0, 0, 0, 0] (chr/day19 input file))
;(expect-focused [0, 5, 0, 0, 0, 0] (chr/day19 real-input real-file))

;[1872 1 1030 1031 1031 257]
;ip=0 [0, 0, 0, 0, 0, 0] seti 5 0 1 [0, 5, 0, 0, 0, 0]
;ip=1 [1, 5, 0, 0, 0, 0] seti 6 0 2 [1, 5, 6, 0, 0, 0]
;ip=2 [2, 5, 6, 0, 0, 0] addi 0 1 0 [3, 5, 6, 0, 0, 0]
;ip=4 [4, 5, 6, 0, 0, 0] setr 1 0 0 [5, 5, 6, 0, 0, 0]
;ip=6 [6, 5, 6, 0, 0, 0] seti 9 0 5 [6, 5, 6, 0, 0, 9]
)