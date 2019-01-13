(ns christmas.chr15.day18-test
  (:require [christmas.chr15.day18 :as chr]
  		  	[expectations :refer :all]
            [clojure.string :as str]))

(comment
(def file (slurp "resources/christmas/chr15/day18real"))

(def t-state-0 (chr/parse-file 
".#.#.#
...##.
#....#
..#...
#.#..#
####.."))

(def t-state-1 (chr/parse-file 
"..##..
..##.#
...##.
......
#.....
#.##.."))

(expect [[1 1][1 2][1 3][2 1][2 3][3 1][3 2][3 3]] (chr/neighbours [2 2]))

(expect t-state-1 (chr/tick t-state-0))

(def status-2-off {[1 1] true [1 2] true [1 3] false [2 1] false [2 2] false [2 3] false [3 1] false [3 2] false [3 3] false})
(def status-3-off {[1 1] true [1 2] true [1 3] true [2 1] false [2 2] false [2 3] false [3 1] false [3 2] false [3 3] false})
(def status-4-off {[1 1] true [1 2] true [1 3] true [2 1] true [2 2] false [2 3] false [3 1] false [3 2] false [3 3] false})
(def status-2-on {[1 1] true [1 2] true [1 3] false [2 1] false [2 2] true [2 3] false [3 1] false [3 2] false [3 3] false})
(def status-3-on {[1 1] true [1 2] true [1 3] true [2 1] false [2 2] true [2 3] false [3 1] false [3 2] false [3 3] false})
(def status-4-on {[1 1] true [1 2] true [1 3] true [2 1] true [2 2] true [2 3] false [3 1] false [3 2] false [3 3] false})

(expect false (chr/on? status-2-off [2 2]))
(expect true (chr/on? status-3-off [2 2]))
(expect false (chr/on? status-4-off [2 2]))
(expect true (chr/on? status-2-on [2 2]))
(expect true (chr/on? status-3-on [2 2]))
(expect false (chr/on? status-4-on [2 2]))

;(expect 768 (chr/day18 file))
;(expect 781 (chr/day18b file))
)