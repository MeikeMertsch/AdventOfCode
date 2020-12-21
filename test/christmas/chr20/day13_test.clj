(ns christmas.chr20.day13-test
  (:require [christmas.chr20.day13 :as chr]
            [expectations :refer :all]
            [clojure.string :as str]))

  (comment
  (def realfilename "resources/chr20/day13 real")
(def filename "resources/chr20/day13")

(expect 295 (chr/day13 filename))
(expect 171 (chr/day13 realfilename))

(expect 1068788 (chr/day13b filename))
  )