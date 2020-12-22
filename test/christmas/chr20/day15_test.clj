(ns christmas.chr20.day15-test
  (:require [christmas.chr20.day15 :as chr]
            [expectations :refer :all]
            [clojure.string :as str]))

(comment
  (def realfilename "resources/chr20/day15 real")
(def filename "resources/chr20/day15")

(expect 436 (chr/day15 filename))
;(expect 1 (chr/day15 realfilename))

;(expect 175594 (chr/day15b filename))
;(expect 1 (chr/day15b realfilename))
 )