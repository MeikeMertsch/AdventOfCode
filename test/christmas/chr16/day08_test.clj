(ns christmas.chr16.day08-test
  (:require [christmas.chr16.day08 :as chr]
  		  	[expectations :refer :all]
            [clojure.string :as str]))

(comment
(def file (slurp "resources/chr16/day08real"))

(def t-file "rect 3x2
rotate column x=1 by 1
rotate row y=0 by 4
rotate column x=1 by 1")

(def t-status-1 {[2 2] false, [0 0] true, [1 0] true, [1 1] true, [4 2] false, [3 0] false, [4 1] false, [5 2] false, [5 1] false, [6 1] false, [0 2] false, [2 0] true, [3 1] false, [2 1] true, [5 0] false, [6 2] false, [6 0] false, [1 2] false, [3 2] false, [0 1] true, [4 0] false})
(def t-status-2 {[2 2] false, [0 0] true, [1 0] false, [1 1] true, [4 2] false, [3 0] false, [4 1] false, [5 2] false, [5 1] false, [6 1] false, [0 2] false, [2 0] true, [3 1] false, [2 1] true, [5 0] false, [6 2] false, [6 0] false, [1 2] true, [3 2] false, [0 1] true, [4 0] false})
(def t-status-3 {[2 2] false, [0 0] false, [1 0] false, [1 1] true, [4 2] false, [3 0] false, [4 1] false, [5 2] false, [5 1] false, [6 1] false, [0 2] false, [2 0] false, [3 1] false, [2 1] true, [5 0] false, [6 2] false, [6 0] true, [1 2] true, [3 2] false, [0 1] true, [4 0] true})

(expect t-status-2 (chr/rotate-column 1 1 7 3 t-status-1))
(expect t-status-3 (chr/rotate-row 0 4 7 3 t-status-2))

(expect 6 (chr/day08 t-file 7 3))
(expect 123 (chr/day08 file 50 6))

(expect [nil nil nil nil nil nil] (chr/day08b file 50 6))
)
