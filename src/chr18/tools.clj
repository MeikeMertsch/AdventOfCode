(ns christmas.tools
	(:require [clojure.string :as str]
		      [expectations :refer :all]))

(def f-test-header "(ns christmas.chr%s.day%s-test
  (:require [christmas.chr%s.day%s :as chr]
  		  	[expectations :refer :all]
            [clojure.string :as str]))

(def file (slurp \"resources/christmas/chr%s/day%sreal\"))")

(def f-header "(ns christmas.chr%s.day%s
  (:require [clojure.string :as str]))")

(def f-path "src/christmas/chr%s/day%s.clj")
(def f-test-path "test/christmas/chr%s/day%s_test.clj")

(defn create-string [string year day]
	(format string year day year day year day))

(defn create-file [file content]
	(spit file content))
	;(println file content))	

(defn prepare [year day]
	(create-file (create-string f-path year day) (create-string f-header year day))
	(create-file (create-string f-test-path year day) (create-string f-test-header year day)))

(defn parse-int [string]
	(Integer/parseInt string))

;(expect nil (prepare "15" "25"))