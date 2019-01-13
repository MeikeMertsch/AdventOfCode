(ns christmas.chr16.day01-test
  (:require [christmas.chr16.day01 :as chr]
  		  	[expectations :refer :all]
            [clojure.string :as str]))

(comment
(def file (slurp "resources/chr16/day01real"))
(def test-2 "R2, R2, R2")
(def test-5 "R2, L3")
(def test-12 "R5, L5, R5, R3")

(def test-b "R8, R4, R4, R8")

(expect 2 (chr/day01 test-2))
(expect 5 (chr/day01 test-5))
(expect 12 (chr/day01 test-12))
(expect 146 (chr/day01 file))

(expect 4 (chr/day01b test-b))
(expect 131 (chr/day01b file))
)