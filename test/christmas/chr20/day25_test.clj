(ns christmas.chr20.day25-test
  (:require [christmas.chr20.day25 :as chr]
            [expectations :refer :all]
            [christmas.tools :as t]
            [clojure.pprint :as pp]
            [clojure.string :as str]))
(comment
  (def realfilename "resources/chr20/day25 real")
(def filename "resources/chr20/day25")

(expect 14897079 (chr/day25 filename))
(expect 354 (chr/day25 realfilename))
)
