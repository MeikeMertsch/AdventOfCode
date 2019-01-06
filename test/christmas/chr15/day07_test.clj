(ns christmas.chr15.day07-test
  (:require [christmas.chr15.day07 :as chr]
  		  	[expectations :refer :all]
            [clojure.string :as str]))

(comment
(def file (slurp "resources/christmas/chr15/day07real"))

(def t-file "123 -> x
456 -> y
x AND y -> d
x OR y -> e
x LSHIFT 2 -> f
y RSHIFT 2 -> g
NOT x -> h
NOT y -> i")

(expect 114 (chr/day07 t-file :g))
(expect 3176 (chr/day07 file :a))
(expect 14710 (chr/day07b file :a))

(expect [3 5] (remove nil? (conj [3 5] nil)))
(expect [3 5 2] (remove nil? (conj [3 5] 2)))
)
