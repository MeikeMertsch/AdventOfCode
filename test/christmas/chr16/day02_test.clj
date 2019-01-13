(ns christmas.chr16.day02-test
  (:require [christmas.chr16.day02 :as chr]
  		  	[expectations :refer :all]
            [clojure.string :as str]))

(comment
(def file (slurp "resources/chr16/day02real"))

(def test-1985 "ULL
RRDDD
LURDL
UUUUD")

(expect "1985" (chr/day02 test-1985))
(expect "82958" (chr/day02 file))

(expect "5DB3" (chr/day02b test-1985))
(expect "B3DB8" (chr/day02b file))
)