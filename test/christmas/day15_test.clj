(ns christmas.day15-test
 (:require [christmas.day15 :as chr]
 		 	[expectations :refer :all]
 		 	[clojure.string :as str]))

(defn get-file [file-name]
	(->> (slurp file-name)
		 str/split-lines))

;(def file (get-file "resources/Input15"))
;(def realFile (get-file "resources/Input15real"))

