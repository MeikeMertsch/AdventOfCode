(ns christmas.chr15.day23-test
  (:require [christmas.chr15.day23 :as chr]
  		  	[expectations :refer :all]
            [clojure.string :as str]))

(comment
(def file (chr/parse-file (slurp "resources/christmas/chr15/day23real")))

(expect 307 (chr/day23 file))
(expect 160 (chr/day23b file))
)