(ns christmas.day14-test
 (:require [christmas.day14 :as chr]
 		 	[expectations :refer :all]
 		 	[clojure.string :as str]))

(defn get-file [file-name]
	(->> (slurp file-name)
		 str/split-lines))

;(def file (get-file "resources/Input14"))
;(def realFile (get-file "resources/Input14real"))

