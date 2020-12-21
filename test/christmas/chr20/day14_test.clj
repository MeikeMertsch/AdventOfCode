(ns christmas.chr20.day14-test
  (:require [christmas.chr20.day14 :as chr]
            [expectations :refer :all]
            [clojure.string :as str]))

 (comment (def realfilename "resources/chr20/day14 real")
(def filename "resources/chr20/day14")

(expect 165 (chr/day14 filename))
(expect 11884151942312 (chr/day14 realfilename))
)