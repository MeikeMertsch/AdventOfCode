(ns christmas.chr20.day12-test
  (:require [christmas.chr20.day12 :as chr]
            [expectations :refer :all]
            [clojure.string :as str]))

(comment
  (def realfilename "resources/chr20/day12 real")
(def filename "resources/chr20/day12")

(expect 25 (chr/day12 filename))
(expect 381 (chr/day12 realfilename))

(expect 286 (chr/day12b filename))
(expect 28591 (chr/day12b realfilename)) ; 11986 too low
)