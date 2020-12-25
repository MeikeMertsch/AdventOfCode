(ns christmas.chr20.day13-test
  (:require [christmas.chr20.day13 :as chr]
            [expectations :refer :all]
            [clojure.string :as str]))

 (comment (def realfilename "resources/chr20/day13 real")
(def filename "resources/chr20/day13")

(expect 295 (chr/day13 filename))
(expect 171 (chr/day13 realfilename))

;(expect 1068781 (chr/day13b filename 100))
(expect 539746751134958 (chr/day13b realfilename 400000000000000))
                                   891300162857101N
 )