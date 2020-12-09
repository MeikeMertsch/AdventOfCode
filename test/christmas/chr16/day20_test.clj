(ns christmas.chr16.day20-test
  (:require [christmas.chr16.day20 :as chr]
  		  	[expectations :refer :all]
            [clojure.string :as str]))

(comment
(def file (slurp "resources/chr16/day20real"))

(expect "" (chr/parse-file file))
)