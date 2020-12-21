(ns christmas.chr20.day16-test
  (:require [christmas.chr20.day16 :as chr]
            [expectations :refer :all]
            [clojure.string :as str]))

(comment
 (def realfilename "resources/chr20/day16 real")
(def filename "resources/chr20/day16")
(def filenameb "resources/chr20/day16b")

(expect 71 (chr/day16 filename))
(expect 23954 (chr/day16 realfilename))

;(expect 71 (chr/day16b filenameb))
(expect 453459307723 (chr/day16b realfilename))
 
 )