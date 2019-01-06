(ns christmas.chr15.day08-test
  (:require [christmas.chr15.day08 :as chr]
  		  	[expectations :refer :all]
            [clojure.string :as str]))

(comment
(def file (str/split-lines (slurp "resources/christmas/chr15/day08real")))
(def t-file (str/split-lines (slurp "resources/christmas/chr15/day08test")))

(expect 38 (count (first file)))
(expect 1333 (chr/day08 file))
(expect 12 (chr/day08 t-file))
(expect 19 (chr/day08b t-file))
(expect 2046 (chr/day08b file))

(expect 29 (chr/count-literals (first file)))
(expect 29 (chr/count-literals (first file)))
)