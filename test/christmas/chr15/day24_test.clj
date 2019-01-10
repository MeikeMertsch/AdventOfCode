(ns christmas.chr15.day24-test
  (:require [christmas.chr15.day24 :as chr]
  			[christmas.tools :as t]
  		  	[expectations :refer :all]
            [clojure.string :as str]))

(comment
(defn get-file [file]
	(->> (slurp file)
		 (str/split-lines) 
		 (map t/parse-int)))

(def file (get-file "resources/christmas/chr15/day24real"))

(expect 520 (chr/compartment-weight file 3))
(expect 11846773891 (chr/day24 file))
(expect 80393059 (chr/day24b file))

(expect true (chr/has-proper-weight? 520 [1 4 13 502]))
)