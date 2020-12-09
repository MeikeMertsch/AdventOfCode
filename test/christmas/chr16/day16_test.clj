(ns christmas.chr16.day16-test
  (:require [christmas.chr16.day16 :as chr]
  		  	[expectations :refer :all]
            [clojure.string :as str]))

(comment
(def file (slurp "resources/chr16/day16real"))
(def disc-length 272)
(def disc-length-2 35651584)

(expect [true false false false true true true false false true true true true false false false false] 
        (chr/parse-file file))

(expect [true false false] (chr/dragon [true]))
(expect [false false true] (chr/dragon [false]))
(expect [true true true true true false false false false false false] 
        (chr/dragon [true true true true true]))

(expect [true false false false false false true true true true false false true false false false false true true true]
        (chr/fill-space 20 (chr/parse-file "10000")))

(expect [false true true true true true false true false true] 
        (chr/checksum [true false false false false false true true true true false false true false false false false true true true]))

(expect "01100" 
        (chr/full-checksum [true false false false false false true true true true false false true false false false false true true true]))

(expect "01100" (chr/hide-traces "10000" 20))
(expect "10010101010011101" (chr/hide-traces file disc-length))
(expect "01100" (chr/hide-traces file disc-length-2))
)
