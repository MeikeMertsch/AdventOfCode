(ns christmas.chr20.day06-test
  (:require [christmas.chr20.day06 :as chr]
            [expectations :refer :all]
            [clojure.string :as str]))

(comment 
  (def realfilename "resources/chr20/day06 real")

(expect 7283 (chr/day06 realfilename)) 
(expect 3520 (chr/day06b realfilename)) )

