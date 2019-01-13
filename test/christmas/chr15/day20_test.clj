(ns christmas.chr15.day20-test
  (:require [christmas.chr15.day20 :as chr]
  		  	[expectations :refer :all]
            [clojure.string :as str]))

(comment
(def input 29000000)

(expect 13 (chr/sum-of-divisors 9))
(expect 1 (chr/sum-of-divisors 1))
(expect [1 3 4 7 6 12 8 15 13] (map chr/sum-of-divisors (range 1 10)))

;(expect 665280 (chr/day20 input)) ; ~ 40s
;(expect 665280 (chr/day20c input)) ; ~ 15 s
)