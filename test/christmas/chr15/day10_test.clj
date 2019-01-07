(ns christmas.chr15.day10-test
  (:require [christmas.chr15.day10 :as chr]
  		  	[expectations :refer :all]
            [clojure.string :as str]))

(comment 
(def input 1113122113)

(expect "11" (chr/day10 1 1))
(expect "21" (chr/day10 1 2))
(expect "1211" (chr/day10 1 3))
(expect "111221" (chr/day10 1 4))
(expect "312211" (chr/day10 1 5))

(expect 360154 (count (chr/day10 input 40)))
(expect 5103798 (count (chr/day10 input 50)))
)