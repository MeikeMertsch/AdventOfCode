(ns christmas.chr16.day07-test
  (:require [christmas.chr16.day07 :as chr]
  		  	[expectations :refer :all]
            [clojure.string :as str]))

(comment
(def file (slurp "resources/chr16/day07real"))
(def test-tfft "abba[mnop]qrst
abcd[bddb]xyyx
aaaa[qwer]tyui
ioxxoj[asdfgh]zxcvbn")

(def testb-tftt 
"aba[bab]xyz
xyx[xyx]xyx
aaa[kek]eke
zazbz[bzb]cdb")

(expect 2 (chr/day07 test-tfft))
(expect 110 (chr/day07 file))

(expect [true false true true] (map chr/supports-ssl? (chr/parse-net testb-tftt)))

(expect 3 (chr/day07b testb-tftt))
(expect 242 (chr/day07b file))
)