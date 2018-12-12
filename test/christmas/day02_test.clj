(ns christmas.day02-test
  (:require [christmas.day02 :as chr]
  		  	[expectations :refer :all]
            [christmas.core :refer :all]
            [clojure.string :as str]))

(def realFile (->> (slurp "resources/Input02real")
		 		   (clojure.string/split-lines)))

(def fileA '("abcdef" "bababc" "abbcde" "abcccd" "aabcdd" "abcdee" "ababab"))
(def fileA '("abcdef" "bababc" "abbcde" "abcccd" "aabcdd" "abcdee" "ababab"))
(def fileB '("abcde" "fghij" "klmno" "pqrst" "fguij" "axcye" "wvxyz"))

(expect 12 (chr/exercise02 fileA))
;(expect 7163 (chr/exercise02 realFile))

(expect "fgij" (chr/exercise02b fileB))
;(expect "ighfbyijnoumxjlxevacpwqtr" (chr/exercise02b realFile))