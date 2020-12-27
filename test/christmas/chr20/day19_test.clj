(ns christmas.chr20.day19-test
  (:require [christmas.chr20.day19 :as chr]
            [expectations :refer :all]
            [clojure.string :as str]))

(comment  (def realfilename "resources/chr20/day19 real")
(def filename "resources/chr20/day19")
(def filename-b "resources/chr20/day19b")

(expect 2 (chr/day19 filename))
(expect 226 (chr/day19 realfilename))

(expect 12 (chr/day19b filename-b))
(expect 355 (chr/day19b realfilename))
)