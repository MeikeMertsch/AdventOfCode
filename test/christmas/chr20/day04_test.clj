(ns christmas.chr20.day04-test
  (:require [christmas.chr20.day04 :as chr]
            [expectations :refer :all]
            [clojure.string :as str]))

(comment
(def filename "resources/chr20/day04")
(def filenameb "resources/chr20/day04b")
(def realfilename "resources/chr20/day04 real")

(expect 2 (chr/day04 filename))
(expect 204 (chr/day04 realfilename)) ; 133 too low

;(expect [false false false false true true true true] (chr/day04b filenameb))
(expect 4 (chr/day04b filenameb))
(expect 179 (chr/day04b realfilename))
)
