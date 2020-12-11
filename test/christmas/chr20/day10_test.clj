(ns christmas.chr20.day10-test
  (:require [christmas.chr20.day10 :as chr]
            [expectations :refer :all]
            [clojure.string :as str]))


(comment
(def realfilename "resources/chr20/day10 real")
(def filename "resources/chr20/day10")
(def filenameb "resources/chr20/day10b")

(expect 35 (chr/day10 filename))
(expect 220 (chr/day10 filenameb))
(expect 2574 (chr/day10 realfilename))

(expect 8 (chr/day10b filename))
(expect 19208 (chr/day10b filenameb))
(expect 2644613988352N (chr/day10b realfilename))
)
