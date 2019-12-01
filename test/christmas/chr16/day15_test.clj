(ns christmas.chr16.day15-test
  (:require [christmas.chr16.day15 :as chr]
  		  	[expectations :refer :all]
            [clojure.string :as str]))

(def file (slurp "resources/chr16/day15real"))

(comment
(expect [[1 17 0 15] [2 3 0 2] [3 19 0 4] [4 13 0 2] [5 7 0 2] [6 5 0 0]] 
    (chr/parse-file file))

(expect [0 1 2 3 4 5 6 0 1 2 3 4 5 6 0 1 2 3 4 5] (take 20 (chr/cyc [5 7 0 2])))

(expect 400589 (chr/get-time file))
(expect 400589 (chr/get-time2 file))
)