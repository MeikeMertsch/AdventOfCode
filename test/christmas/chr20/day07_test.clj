(ns christmas.chr20.day07-test
  (:require [christmas.chr20.day07 :as chr]
            [expectations :refer :all]
            [clojure.string :as str]))

(comment 
(def realfilename "resources/chr20/day07 real")
(def filename "resources/chr20/day07")
(def filenameb "resources/chr20/day07b")

(expect 4 (chr/day07 filename))
(expect 252 (chr/day07 realfilename))

(def bag-field (->> (chr/parsefile filename) (hash-map :found #{} :active ["shiny gold"] :bags)))

(expect 32 (chr/day07b filename))
(expect 126 (chr/day07b filenameb))
(expect 35487 (chr/day07b realfilename)) ; 35439 too low
(expect [["vibrant plum" 4] ["dark olive" 2]] (chr/find-contained-bags ["shiny gold" 2] bag-field))
)
