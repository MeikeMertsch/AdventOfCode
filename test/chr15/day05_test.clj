(ns christmas.chr15.day05-test
  (:require [christmas.chr15.day05 :as chr]
  		  	[expectations :refer :all]
            [clojure.string :as str]))

(comment
(def file (str/split-lines (slurp "resources/christmas/chr15/day05real")))

(expect 1000 (count file))

(expect true (chr/has-three-vowels? "ugknbfddgicrmopn"))
(expect true (chr/has-three-vowels? "jchzalrnumimnmhp"))
(expect true (chr/has-three-vowels? "haegwjzuvuyypxyu"))
(expect false (chr/has-three-vowels? "dvszwmarrgswjxmb"))
(expect true (chr/has-three-vowels? "aaa"))

(expect true (chr/has-duplicates? "ugknbfddgicrmopn"))
(expect false (chr/has-duplicates? "jchzalrnumimnmhp"))
(expect true (chr/has-duplicates? "haegwjzuvuyypxyu"))
(expect true (chr/has-duplicates? "dvszwmarrgswjxmb"))
(expect true (chr/has-duplicates? "aaa"))

(expect true (chr/not-has-forbidden-combs? "ugknbfddgicrmopn"))
(expect true (chr/not-has-forbidden-combs? "jchzalrnumimnmhp"))
(expect false (chr/not-has-forbidden-combs? "haegwjzuvuyypxyu"))
(expect true (chr/not-has-forbidden-combs? "dvszwmarrgswjxmb"))
(expect true (chr/not-has-forbidden-combs? "aaa"))

(expect true (chr/nice? "ugknbfddgicrmopn"))
(expect false (chr/nice? "jchzalrnumimnmhp"))
(expect false (chr/nice? "haegwjzuvuyypxyu"))
(expect false (chr/nice? "dvszwmarrgswjxmb"))
(expect true (chr/nice? "aaa"))

(expect 238 (chr/day05 file))

(expect true (chr/contains-repeater? "qjhvhtzxzqqjkmpb"))
(expect true (chr/contains-repeater? "xxyxx"))
(expect true (chr/contains-repeater? "ieodomkazucvgmuy"))
(expect nil (chr/contains-repeater? "uurcxstgmygtbstg"))

(expect true (chr/contains-dupe? "qjhvhtzxzqqjkmpb"))
(expect true (chr/contains-dupe? "xxyxx"))
(expect true (chr/contains-dupe? "xyxy"))
(expect true (chr/contains-dupe? "aabcdefgaa"))
(expect false (chr/contains-dupe? "aaa"))
(expect true (chr/contains-dupe? "uurcxstgmygtbstg"))
(expect false (chr/contains-dupe? "ieodomkazucvgmuy"))

(expect true (chr/check-replace "uurcxstgmygtbstg" ["tg" 3]))
(expect false (chr/check-replace "aaa" ["aa" 3]))

(expect true (chr/nice-2? "qjhvhtzxzqqjkmpb"))
(expect true (chr/nice-2? "xxyxx"))
(expect false (chr/nice-2? "uurcxstgmygtbstg"))
(expect false (chr/nice-2? "ieodomkazucvgmuy"))

(expect 69 (chr/day05b file))
)