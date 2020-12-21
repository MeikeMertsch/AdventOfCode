(ns christmas.chr20.day11-test
  (:require [christmas.chr20.day11 :as chr]
            [expectations :refer :all]
            [clojure.string :as str]))

(comment
  (def realfilename "resources/chr20/day11 real")
(def filename "resources/chr20/day11")
(def empty-game {[7 6] false, [8 7] false, [9 8] false, [7 1] false, [8 9] false, [4 3] false, [2 2] false, [0 0] false, [3 9] false, [7 7] false, [1 0] false, [8 4] false, [7 2] false, [7 4] false, [8 3] false, [0 6] false, [3 3] false, [5 4] false, [1 1] false, [0 5] false, [7 3] false, [8 6] false, [4 2] false, [7 8] false, [3 0] false, [9 0] false, [9 6] false, [1 9] false, [5 3] false, [9 9] false, [9 3] false, [4 9] false, [0 9] false, [8 0] false, [5 2] false, [4 6] false, [1 4] false, [8 2] false, [1 3] false, [4 8] false, [1 5] false, [1 8] false, [6 4] false, [0 3] false, [5 6] false, [5 8] false, [8 5] false, [5 5] false, [7 9] false, [2 7] false, [5 9] false, [2 4] false, [3 6] false, [9 2] false, [4 5] false, [7 0] false, [0 2] false, [2 0] false, [3 1] false, [9 5] false, [3 8] false, [9 4] false, [1 6] false, [7 5] false, [5 0] false, [6 2] false, [1 2] false, [3 5] false, [0 8] false, [3 2] false, [4 0] false})

(expect 37 (chr/day11 filename))
;(expect 37 (chr/day11 realfilename))
(expect [[1 4] [1 5] [1 6] [2 4] [2 6] [3 4] [3 5] [3 6]] (chr/neighbor [2 5]))
(expect [[2 5] true] (chr/tick-space empty-game [2 5]))
  
(expect [[1 1] [1 2] [1 3] [3 1] [3 2] [3 3] [2 0] [2 4]] (chr/visible empty-game [2 2]))
)