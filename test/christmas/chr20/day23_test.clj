(ns christmas.chr20.day23-test
  (:require [christmas.chr20.day23 :as chr]
            [expectations :refer :all]
            [christmas.tools :as t]
            [clojure.pprint :as pp]
            [clojure.string :as str]))
(comment
(def real-input [5 8 9 1 7 4 2 6 3])
(def input [3 8 9 1 2 5 4 6 7])

(expect 67384529 (chr/day23 input 100))
(expect 92658374 (chr/day23 input 10))
(expect 43896725 (chr/day23 real-input 100))

(def game {:ring {7 3, 1 2, 4 6, 6 7, 3 8, 2 5, 9 1, 5 4, 8 9}, :current 3 :cups 9})

(expect 8 (:destination (chr/find-destination (assoc game :triplet [2 1 9]))))

;(expect 149245887792 (chr/day23b input 10000000 1000000))
;(expect 67384529 (chr/day23b input 100 9))
(pp/pprint (chr/day23b real-input 10 100)))