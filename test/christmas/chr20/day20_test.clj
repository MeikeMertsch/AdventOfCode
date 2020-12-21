(ns christmas.chr20.day20-test
  (:require [christmas.chr20.day20 :as chr]
            [expectations :refer :all]
            [christmas.tools :as t]
            [clojure.pprint :as pp]
[clojure.string :as str]))

(comment
(def realfilename "resources/chr20/day20 real")
(def filename "resources/chr20/day20")

(expect 20899048083289 (chr/day20 filename))
(expect 15405893262491 (chr/day20 realfilename))

(pp/pprint (chr/day20b filename))
;(expect 1 (chr/day20b realfilename))
)