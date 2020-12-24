(ns christmas.chr20.day24-test
  (:require [christmas.chr20.day24 :as chr]
            [expectations :refer :all]
            [christmas.tools :as t]
            [clojure.pprint :as pp]
            [clojure.string :as str]))

(comment
  (def realfilename "resources/chr20/day24 real")
(def filename "resources/chr20/day24")

(expect 10 (chr/day24 filename))
(expect 354 (chr/day24 realfilename))

(expect [6 0] (chr/parse-line "esenee"))

;(expect 2208 (chr/day24b filename))
;(expect 2208 (chr/day24b realfilename))
(expect #{[8 0][4 0][7 2][7 -2][5 2][5 -2]} (set (chr/hex-neighbors [6 0]))))