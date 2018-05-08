package com.parcial.appsmoviles.parcial.Databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by carlo on 7/05/2018.
 */

public class Tienda extends SQLiteOpenHelper {

    String query_Creator = "SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;\n" +
            "SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;\n" +
            "SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';\n" +
            "\n" +
            "-- -----------------------------------------------------\n" +
            "-- Schema Tienda\n" +
            "-- -----------------------------------------------------\n" +
            "\n" +
            "-- -----------------------------------------------------\n" +
            "-- Schema Tienda\n" +
            "-- -----------------------------------------------------\n" +
            "CREATE SCHEMA IF NOT EXISTS `Tienda` DEFAULT CHARACTER SET utf8 ;\n" +
            "USE `Tienda` ;\n" +
            "\n" +
            "-- -----------------------------------------------------\n" +
            "-- Table `Tienda`.`Cliente`\n" +
            "-- -----------------------------------------------------\n" +
            "CREATE TABLE IF NOT EXISTS `Tienda`.`Cliente` (\n" +
            "  `idCliente` VARCHAR(50) NOT NULL,\n" +
            "  `Nombre` VARCHAR(45) NOT NULL,\n" +
            "  `Telefono` VARCHAR(45) NOT NULL,\n" +
            "  `Direccion` VARCHAR(45) NULL,\n" +
            "  PRIMARY KEY (`idCliente`))\n" +
            "ENGINE = InnoDB;\n" +
            "\n" +
            "\n" +
            "-- -----------------------------------------------------\n" +
            "-- Table `Tienda`.`Venta`\n" +
            "-- -----------------------------------------------------\n" +
            "CREATE TABLE IF NOT EXISTS `Tienda`.`Venta` (\n" +
            "  `idVenta` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `idCliente` VARCHAR(45) NULL,\n" +
            "  `totalVenta` DOUBLE NULL,\n" +
            "  PRIMARY KEY (`idVenta`),\n" +
            "  INDEX `idCliente_idx` (`idCliente` ASC),\n" +
            "  CONSTRAINT `idCliente`\n" +
            "    FOREIGN KEY (`idCliente`)\n" +
            "    REFERENCES `Tienda`.`Cliente` (`idCliente`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE CASCADE)\n" +
            "ENGINE = InnoDB;\n" +
            "\n" +
            "\n" +
            "-- -----------------------------------------------------\n" +
            "-- Table `Tienda`.`Producto`\n" +
            "-- -----------------------------------------------------\n" +
            "CREATE TABLE IF NOT EXISTS `Tienda`.`Producto` (\n" +
            "  `idProducto` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `nombreProducto` VARCHAR(45) NOT NULL,\n" +
            "  `tipoProducto` VARCHAR(1) NOT NULL,\n" +
            "  `precio` DOUBLE NOT NULL,\n" +
            "  PRIMARY KEY (`idProducto`))\n" +
            "ENGINE = InnoDB;\n" +
            "\n" +
            "\n" +
            "-- -----------------------------------------------------\n" +
            "-- Table `Tienda`.`ventaDetalle`\n" +
            "-- -----------------------------------------------------\n" +
            "CREATE TABLE IF NOT EXISTS `Tienda`.`ventaDetalle` (\n" +
            "  `idVenta` INT NOT NULL,\n" +
            "  `idProducto` INT NOT NULL,\n" +
            "  `Cantidad` VARCHAR(45) NOT NULL,\n" +
            "  INDEX `idVenta_idx` (`idVenta` ASC),\n" +
            "  INDEX `idProducto_idx` (`idProducto` ASC),\n" +
            "  CONSTRAINT `idVenta`\n" +
            "    FOREIGN KEY (`idVenta`)\n" +
            "    REFERENCES `Tienda`.`Venta` (`idVenta`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION,\n" +
            "  CONSTRAINT `idProducto`\n" +
            "    FOREIGN KEY (`idProducto`)\n" +
            "    REFERENCES `Tienda`.`Producto` (`idProducto`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION)\n" +
            "ENGINE = InnoDB;\n" +
            "\n" +
            "\n" +
            "-- -----------------------------------------------------\n" +
            "-- Table `Tienda`.`Usuario`\n" +
            "-- -----------------------------------------------------\n" +
            "CREATE TABLE IF NOT EXISTS `Tienda`.`Usuario` (\n" +
            "  `nickname` INT NOT NULL,\n" +
            "  `password` VARCHAR(45) NOT NULL,\n" +
            "  PRIMARY KEY (`nickname`))\n" +
            "ENGINE = InnoDB;\n" +
            "\n" +
            "\n" +
            "SET SQL_MODE=@OLD_SQL_MODE;\n" +
            "SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;\n" +
            "SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;\n";

    public Tienda(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(query_Creator);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
