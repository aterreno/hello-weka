(ns hello-weka.core
  (:use [clojure.java.io])
  (:require [iota])
  (import weka.core.converters.CSVLoader
          weka.core.converters.ArffSaver
          weka.core.Instances
          java.io.File)
  (:gen-class))

(defn write-arff [loader path]
  (doto (ArffSaver.)
    (.setInstances (.getDataSet loader))
        (.setFile (File. path))
        (.setDestination (File. path))
        (.writeBatch)))

(defn load-csv[path]
  (doto (CSVLoader.)
    (.setSource (File. path))))

(defn transform [csv arff]
  (write-arff (load-csv csv) arff))

(defn -main [& args]
  (when-not (= 2 (count args))
    (throw (Exception. "Usage: transform input_file output_file")))
  (transform (first args) (last args)))
