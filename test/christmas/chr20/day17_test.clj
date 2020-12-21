(ns christmas.chr20.day17-test
  (:require [christmas.chr20.day17 :as chr]
            [expectations :refer :all]
            [clojure.string :as str]))

(comment
 (def realfilename "resources/chr20/day17 real")
(def filename "resources/chr20/day17")

(expect 112 (chr/day17 3 filename))
(expect 313 (chr/day17 3 realfilename))

(expect 2640 (chr/day17 4 realfilename))
)