(ns christmas.chr20.day09-test
  (:require [christmas.chr20.day09 :as chr]
            [expectations :refer :all]
            [clojure.string :as str]))

(comment
  
  (def realfilename "resources/chr20/day09 real")
(def filename "resources/chr20/day09")

(expect 127 (chr/day09 filename 5))
(expect 507622668 (chr/day09 realfilename 25))

(expect 62 (chr/day09b filename 127))
(expect 76688505N (chr/day09b realfilename 507622668N)))



