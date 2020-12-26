(ns christmas.chr20.day22-test
  (:require [christmas.chr20.day22 :as chr]
            [expectations :refer :all]
            [christmas.tools :as t]
            [clojure.pprint :as pp]
            [clojure.string :as str]))

(comment
  (def realfilename "resources/chr20/day22 real")
(def filename "resources/chr20/day22")

(expect 306 (chr/day22 filename))
(expect 35370 (chr/day22 realfilename))

(expect 291 (chr/day22b filename))
(expect 36246 (chr/day22b realfilename))
)