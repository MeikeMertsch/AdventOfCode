(ns christmas.chr16.day03-test
  (:require [christmas.chr16.day03 :as chr]
  		  	[expectations :refer :all]
            [clojure.string :as str]))

(comment
(def file (slurp "resources/chr16/day03real"))

(def test-f "5 10 25")
(def test-b 
"101 301 501
102 302 502
103 303 503
201 401 601
202 402 602
203 403 603")

(expect false (chr/triangle? (chr/parse-line test-f)))
(expect 0 (chr/day03 test-f))

(expect 1032 (chr/day03 file))

(expect 6 (chr/day03b test-b))
(expect 1838 (chr/day03b file)) 
)