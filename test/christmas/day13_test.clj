(ns christmas.day13-test
 (:require [christmas.day13 :as chr]
 		 	[expectations :refer :all]
 		 	[clojure.string :as str]))

(defn get-file [file-name]
	(->> (slurp file-name)
			    str/split-lines))

;(def file (get-file "resources/Input13"))
;(def realFile (get-file "resources/Input13real"))

