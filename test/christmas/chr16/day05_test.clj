(ns christmas.chr16.day05-test
  (:require [christmas.chr16.day05 :as chr]
  		  	[expectations :refer :all]
            [clojure.string :as str]))

(comment
(def file (slurp "resources/chr16/day05real"))

(def test-door-id "abc")
(expect "00000155f8105dff7f56ee10fa9b9abd" (chr/md5 "abc" "3231929"))

;(expect [3231930 \1] (chr/next-digit "abc" [0 0]))
;(expect "18f47a30" (chr/day05 "abc")) ; long running
;(expect "05ace8e3" (chr/day05b "abc")) ; long running

;(expect "4543c154" (chr/day05 file)) ; ~ 70s
;(expect-focused "1050cbbd" (chr/day05b file)) ; ~ 190 s 

)