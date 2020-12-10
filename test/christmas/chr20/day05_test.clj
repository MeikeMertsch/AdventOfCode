(ns christmas.chr20.day05-test
  (:require [christmas.chr20.day05 :as chr]
            [expectations :refer :all]
            [clojure.string :as str]))

(comment
(def realfilename "resources/chr20/day05 real")

(expect 204 (chr/day05 realfilename)) ; 133 too low
)
