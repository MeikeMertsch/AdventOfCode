(ns christmas.chr16.day06-test
  (:require [christmas.chr16.day06 :as chr]
  		  	[expectations :refer :all]
            [clojure.string :as str]))

(comment
(def file (slurp "resources/chr16/day06real"))

(def test-string 
	"eedadn
drvtee
eandsr
raavrd
atevrs
tsrnev
sdttsa
rasrtv
nssdts
ntnada
svetve
tesnvt
vntsnd
vrdear
dvrsen
enarar")

(expect "easter" (chr/day06 test-string))
(expect "ikerpcty" (chr/day06 file))
(expect "advent" (chr/day06b test-string))
(expect "uwpfaqrq" (chr/day06b file))
)