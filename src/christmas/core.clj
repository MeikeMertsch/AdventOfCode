(ns christmas.core
  (:gen-class)
  (:require [cheshire.core :refer :all]))

(defn import-file [file]
	(-> (slurp file)
		(parse-string true)
		))


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
