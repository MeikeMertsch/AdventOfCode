(ns christmas.chr15.day11-test
  (:require [christmas.chr15.day11 :as chr]
  		  	[christmas.tools :as t]
  		  	[expectations :refer :all]
            [clojure.string :as str]))

(comment
(def input "vzbxkghb")

(expect "abcdefgi" (chr/increase "abcdefgh"))

(expect "abcdffaa" (chr/day11 "abcdefgh"))
;(expect "ghjaabcc" (chr/day11 "ghijklmn"))

(expect "vzbxxyzz" (chr/day11 input))
(expect "vzcaabcc" (chr/day11 (chr/increase input)))

(expect [0 1 1 2 4 5 5 6] (chr/transform "abbceffg"))
(expect 321784924 (chr/to-number (chr/transform "abbceffg")))
(expect [0 1 1 2 4 5 5 6] (chr/to-seq (chr/to-number (chr/transform "abbceffg"))))
(expect "abbceffg" (chr/back-transform [0 1 1 2 4 5 5 6]))
)

;(def file-2 (slurp "resources/christmas/chr15/day12real"))
; day 12 part 1 (expect 0 (apply + (map t/parse-int (re-seq #"-*\d+" file-2))))